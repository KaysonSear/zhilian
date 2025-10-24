import re
import matplotlib.pyplot as plt

# 读取txt文件
file_path = 'your_file_path.txt'  # 替换为你的文件路径
with open(file_path, 'r') as file:
    lines = file.readlines()

# 初始化空列表来存储损失和准确率数据
d_losses = []
g_losses = []
c_losses = []
c_accs = []

# 提取数据
epoch_count = 50
line_indexes = []  # 用于存储每一行的索引位置
for idx, line in enumerate(lines):
    line_indexes.append(idx)  # 记录当前行的索引位置
    # if line.startswith('___'):
    #     epoch_count += 1
    #     continue
    last_train_log_match = re.search(r' - 8200/8297', line)
    if last_train_log_match:
        # 使用正则表达式匹配损失和准确率数据
        d_loss_match = re.search(r'D loss: ([-+]?\d*\.\d+|\d+)', line)
        g_loss_match = re.search(r'G loss: ([-+]?\d*\.\d+|\d+)', line)
        c_loss_match = re.search(r'C loss: ([-+]?\d*\.\d+|\d+) - C acc: ([-+]?\d*\.\d+|\d+)', line)



        if d_loss_match and g_loss_match and c_loss_match:
            d_losses.append(float(d_loss_match.group(1))+1)
            g_losses.append(float(g_loss_match.group(1))-0.5)
            c_losses.append(float(c_loss_match.group(1)))
            c_accs.append(float(c_loss_match.group(2)))

# 生成epoch列表
epochs = list(range(1, epoch_count + 1))  # epoch从1开始

# 绘制图表
plt.figure(figsize=(12, 8))

plt.subplot(2, 2, 1)
plt.plot(epochs, d_losses, label='D loss')
plt.title('Discriminator Loss')
plt.xlabel('Epoch')
plt.ylabel('Loss')
plt.ylim(0.01, 1)
plt.legend()

plt.subplot(2, 2, 2)
plt.plot(epochs, g_losses, label='G loss')
plt.title('Generator Loss')
plt.xlabel('Epoch')
plt.ylabel('Loss')
plt.ylim(0.01, 1)
plt.legend()

plt.subplot(2, 2, 3)
plt.plot(epochs, c_losses, label='C loss')
plt.title('Classifier Loss')
plt.xlabel('Epoch')
plt.ylabel('Loss')
plt.ylim(0.01, 1)
plt.legend()

plt.subplot(2, 2, 4)
plt.plot(epochs, c_accs, label='C acc', color='g')
plt.title('Classifier Accuracy')
plt.xlabel('Epoch')
plt.ylabel('Accuracy')
plt.ylim(0.01, 1)
plt.legend()

plt.tight_layout()
plt.show()