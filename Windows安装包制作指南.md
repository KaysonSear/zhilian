# 🪟 Windows安装包制作指南

## 📦 傻瓜式方案 - 使用GitHub自动构建

这个方案**完全不需要安装任何开发工具**，只需要有GitHub账号即可！

### 第一步：将代码上传到GitHub

1. **创建GitHub账号**（如果还没有）
   - 访问 https://github.com
   - 点击"Sign up"注册账号（免费）

2. **创建一个新仓库**
   - 登录GitHub后，点击右上角的"+"号
   - 选择"New repository"
   - 仓库名称填写：`zhilian-security`
   - 选择"Private"（私有仓库，只有你能看到）
   - 点击"Create repository"

3. **上传代码到GitHub**
   
   在你的终端中运行：
   
   ```bash
   cd /media/kayson/F0001DC5001D9426/Coding/zhilian-next
   
   # 初始化Git仓库
   git init
   
   # 添加所有文件
   git add .
   
   # 提交代码
   git commit -m "初始提交：智链安全平台"
   
   # 关联GitHub仓库（替换YOUR_USERNAME为你的GitHub用户名）
   git remote add origin https://github.com/YOUR_USERNAME/zhilian-security.git
   
   # 推送代码
   git branch -M main
   git push -u origin main
   ```

### 第二步：自动构建Windows安装包

1. **触发自动构建**
   - 代码推送成功后，GitHub会自动开始构建
   - 你可以在GitHub仓库页面点击"Actions"标签查看构建进度
   
2. **等待构建完成**（大约15-20分钟）
   - GitHub会自动在Windows环境中编译你的应用
   - 构建过程完全自动化，无需任何操作

3. **下载安装包**
   
   构建完成后，有两种方式获取安装包：
   
   **方式A：从Artifacts下载（临时文件，90天后自动删除）**
   - 在GitHub仓库页面点击"Actions"
   - 找到最新的"构建Tauri应用"工作流
   - 点击进入，在页面底部找到"Artifacts"
   - 下载`windows-installer`压缩包
   - 解压后得到`.msi`安装文件
   
   **方式B：从Release下载（永久保存）**
   - 在GitHub仓库页面点击"Releases"标签
   - 找到最新的草稿版本（Draft）
   - 点击"Edit"编辑
   - 点击"Publish release"发布
   - 现在可以看到`.msi`安装包，右键复制下载链接

### 第三步：发给同事安装

1. **下载.msi文件到本地**

2. **发送给Windows同事**
   - 可以通过QQ、微信、邮件等方式发送
   - 或者直接把GitHub Release链接发给他

3. **同事安装步骤**（超级简单！）
   - 双击`.msi`文件
   - 点击"下一步"按照向导安装
   - 安装完成后，在开始菜单搜索"智链"
   - 双击启动，就像打开QQ、微信一样！

---

## 🎯 快速命令参考

如果你已经配置好GitHub，想要更新安装包：

```bash
cd /media/kayson/F0001DC5001D9426/Coding/zhilian-next

# 提交新的更改
git add .
git commit -m "更新功能：添加真实中国地图"
git push

# 等待GitHub自动构建完成（15-20分钟）
# 然后去GitHub下载新的安装包
```

---

## 🔧 手动触发构建

如果想在不修改代码的情况下重新构建：

1. 访问你的GitHub仓库
2. 点击"Actions"标签
3. 点击左侧的"构建Tauri应用"
4. 点击右侧的"Run workflow"按钮
5. 选择"main"分支
6. 点击"Run workflow"确认

---

## ❓ 常见问题

### Q1: 我没有GitHub账号怎么办？
**A:** GitHub是免费的，注册只需要邮箱，强烈建议注册！这是目前最简单的跨平台构建方案。

### Q2: 能不能本地构建？
**A:** 可以，但需要在Windows电脑上安装Node.js、Rust等开发工具，不推荐非技术人员使用。

### Q3: 构建失败了怎么办？
**A:** 在GitHub Actions页面点击失败的任务，查看红色错误信息，或者联系我帮你排查。

### Q4: .msi文件多大？
**A:** 大约50-80MB，包含了完整的应用和运行时环境。

### Q5: 同事安装后需要联网吗？
**A:** 不需要！安装包是独立的，安装后可以离线使用（地图数据除外，首次加载需要联网）。

### Q6: 可以制作绿色版（免安装）吗？
**A:** 构建时会同时生成`.exe`绿色版和`.msi`安装版，都可以使用。

---

## 🎉 总结

使用GitHub Actions方案的优势：

✅ **完全自动化** - 推送代码后自动构建，无需手动操作  
✅ **跨平台支持** - 在Linux开发，自动构建Windows版本  
✅ **专业级CI/CD** - 使用GitHub的专业构建服务器  
✅ **永久保存** - Release版本永久保存，随时下载  
✅ **免费使用** - GitHub Actions对公开和私有仓库都免费提供构建时间  

---

## 📞 需要帮助？

如果在使用过程中遇到任何问题，可以：
1. 查看GitHub Actions的构建日志
2. 检查`.github/workflows/build-tauri.yml`配置文件
3. 确保`apps/desktop/src-tauri/tauri.conf.json`配置正确

祝你构建顺利！🚀

