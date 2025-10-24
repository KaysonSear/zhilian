import matplotlib.pyplot as plt

# 模拟数据
thresholds = [0.5, 0.6, 0.7, 0.8, 0.9]
accuracy = [98.1, 98.3, 98.6, 99.0, 99.2]  # 替换为实际准确率数据

# 绘制折线图
plt.figure(figsize=(8, 6))
plt.plot(thresholds, accuracy, marker='o', linestyle='-', color='b', label='DDos识别为未知攻击准确率')

# 添加标签和标题
plt.xlabel('阈值')
plt.ylabel('准确率 (%)')
plt.title('DDos攻击识别为未知攻击的准确率')
plt.grid(True)
plt.ylim(98, 100)

# 显示图例
plt.legend()

# 显示图形
plt.tight_layout()
plt.show()
