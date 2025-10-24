import pandas as pd

# 读取数据
data = pd.read_csv("../data/2024-06-14_Flow.csv",encoding="GBK")

# 删除指定的列，假设列名包含了逗号
columns_to_drop = ['Flow ID', 'Src IP', 'Dst IP', 'Timestamp','Protocol','Src Port']  # 列名列表

# 删除列
data.drop(columns=columns_to_drop, inplace=True, errors='ignore')

# 找到插入位置
insert_position = data.columns.get_loc('Bwd Segment Size Avg') + 1

# 在指定位置插入新列，并将其值初始化为 0
data.insert(insert_position, 'fwd_header_length', 0)

# 打印更新后的数据列名，确保新列插入正确
print("Columns after insertion:\n", data.columns.value_counts())


# data1 = pd.read_csv("../data/clean2.csv")
# print("Original columns:\n", data1.columns)


