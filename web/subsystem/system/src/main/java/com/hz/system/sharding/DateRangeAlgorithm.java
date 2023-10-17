package com.hz.system.sharding;

import com.google.common.collect.Range;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.sharding.ShardingAutoTableAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @explain
 * @Classname TimeShardingAlgorithm
 * @Date 2023/10/12 14:30
 * @Created by chrise warner
 */
@Slf4j
public class DateRangeAlgorithm implements StandardShardingAlgorithm<Date>, ShardingAutoTableAlgorithm {

    @Override
    public String getType() {
        return "AUTO_CUSTOM";
    }

    /**
     * 分片时间格式
     */
    private static final DateTimeFormatter TABLE_SHARD_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMM");

    /**
     * 完整时间格式
     */
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");

    /**
     * 分隔符符号为 "_"
     */
    private final String TABLE_SPLIT_SYMBOL = "_";

    @Getter
    private Properties props;

    @Getter
    private int autoTablesAmount;

    @Override
    public void init(final Properties props) {
        this.props = props;
    }

    /**
     * 查询要操作的表名
     * @param availableTargetNames: 可用的分片名集合(分库就是库名，分表就是表名)
     * @param preciseShardingValue: 分片键
     * @return 表名
     */
    @Override
    public String doSharding(final Collection<String> availableTargetNames, final PreciseShardingValue<Date> preciseShardingValue) {
        String logicTableName = preciseShardingValue.getLogicTableName();
        Date dateTime = preciseShardingValue.getValue();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String resultTableName = logicTableName + "_" + sdf.format(dateTime);
        // 判断缓存中是否有表名称，如果没有则需要初始化
        if(availableTargetNames.contains(resultTableName)){
            return resultTableName;
        }
        String tableName = getShardingTableAndCreate(logicTableName, resultTableName, availableTargetNames);
        List<String> allTableNameBySchema = ShardingAlgorithmUtil.getAllTableNameBySchema(logicTableName);
        availableTargetNames.clear();
        availableTargetNames.addAll(allTableNameBySchema);
        autoTablesAmount = allTableNameBySchema.size();
        return tableName;
    }

    /**
     * 时间范围分表逻辑
     * @param availableTargetNames
     * @param rangeShardingValue
     * @return
     */
    @Override
    public Collection<String> doSharding(final Collection<String> availableTargetNames, final RangeShardingValue<Date> rangeShardingValue) {
        String logicTableName = rangeShardingValue.getLogicTableName();
        // between and 的起始值
        Range<Date> valueRange = rangeShardingValue.getValueRange();
        boolean hasLowerBound = valueRange.hasLowerBound();
        boolean hasUpperBound = valueRange.hasUpperBound();
        // 获取最大值和最小值
        LocalDateTime min = hasLowerBound ?
                valueRange.lowerEndpoint().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                : getLowerEndpoint(availableTargetNames);
        LocalDateTime max = hasUpperBound ?
                valueRange.upperEndpoint().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                : getUpperEndpoint(availableTargetNames);
        // 循环计算分表范围
        Set<String> resultTableNames = new LinkedHashSet<>();
        while (min.isBefore(max) || min.equals(max)) {
            String tableName = logicTableName + TABLE_SPLIT_SYMBOL + min.format(TABLE_SHARD_TIME_FORMATTER);
            resultTableNames.add(tableName);
            min = min.plusMinutes(1);
        }
        return getShardingTablesAndCreate(logicTableName, resultTableNames, availableTargetNames);
    }

    /**
     * 检查分表获取的表名是否存在，不存在则自动建表
     *
     * @param logicTableName       逻辑表
     * @param resultTableNames     真实表名，例：t_user_202201
     * @param availableTargetNames 可用的数据库表名
     * @return 存在于数据库中的真实表名集合
     */
    public Set<String> getShardingTablesAndCreate(String logicTableName, Collection<String> resultTableNames, Collection<String> availableTargetNames) {
        return resultTableNames.stream().map(o -> getShardingTableAndCreate(logicTableName, o, availableTargetNames)).collect(Collectors.toSet());
    }

    /**
     * 检查分表获取的表名是否存在，不存在则自动建表
     *
     * @param logicTableName  逻辑表
     * @param resultTableName 真实表名，例：t_user_202201
     * @return 确认存在于数据库中的真实表名
     */
    private String getShardingTableAndCreate(String logicTableName, String resultTableName, Collection<String> availableTargetNames) {
        // 缓存中有此表则返回，没有则判断创建
        if (availableTargetNames.contains(resultTableName)) {
            return resultTableName;
        } else {
            // 检查分表获取的表名不存在，需要自动建表
            boolean isSuccess = ShardingAlgorithmUtil.createShardingTable(logicTableName, resultTableName);
            if (isSuccess) {
                // 如果建表成功，需要更新缓存
                availableTargetNames.add(resultTableName);
                autoTablesAmount++;
                return resultTableName;
            } else {
                // 如果建表失败，返回逻辑空表
                return logicTableName;
            }
        }
    }

    /**
     * 获取 最小分片值
     *
     * @param tableNames 表名集合
     * @return 最小分片值
     */
    private LocalDateTime getLowerEndpoint(Collection<String> tableNames) {
        Optional<LocalDateTime> optional = tableNames.stream()
                .map(o -> LocalDateTime.parse(o.replace(TABLE_SPLIT_SYMBOL, "") + "01 00:00:00", DATE_TIME_FORMATTER))
                .min(Comparator.comparing(Function.identity()));
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new IllegalArgumentException("获取数据最小分表失败，请稍后重试");
        }
    }

    /**
     * 获取 最大分片值
     *
     * @param tableNames 表名集合
     * @return 最大分片值
     */
    private LocalDateTime getUpperEndpoint(Collection<String> tableNames) {
        Optional<LocalDateTime> optional = tableNames.stream()
                .map(o -> LocalDateTime.parse(o.replace(TABLE_SPLIT_SYMBOL, "") + "01 00:00:00", DATE_TIME_FORMATTER))
                .max(Comparator.comparing(Function.identity()));
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new IllegalArgumentException("获取数据最大分表失败，请稍后重试");
        }
    }
}
