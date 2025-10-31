# Tauri桌面应用构建指南

## 📦 快速打包

### 方法1：开发模式（需要Web服务器运行）

这个方法会创建一个桌面窗口，显示localhost:3000的内容。

**步骤：**

1. **启动Web开发服务器（如果还没启动）：**
```bash
cd /media/kayson/F0001DC5001D9426/Coding/zhilian-next
pnpm --filter @zhilian/web dev
```

2. **在另一个终端启动Tauri开发模式：**
```bash
cd /media/kayson/F0001DC5001D9426/Coding/zhilian-next/apps/desktop
source ~/.cargo/env
pnpm tauri dev
```

这会打开一个桌面窗口，里面显示你的Web应用！

---

### 方法2：独立可执行文件（推荐给Windows队友）

这个方法会创建一个完全独立的`.msi`安装包或`.exe`文件。

**步骤：**

1. **构建Tauri应用：**
```bash
cd /media/kayson/F0001DC5001D9426/Coding/zhilian-next/apps/desktop
source ~/.cargo/env
pnpm tauri build
```

2. **等待编译（可能需要5-15分钟）**

3. **找到生成的文件：**
   - Linux: `apps/desktop/src-tauri/target/release/bundle/appimage/`
   - Windows交叉编译的MSI: `apps/desktop/src-tauri/target/release/bundle/msi/`

---

## 🪟 为Windows系统构建

### 选项A：在Linux上交叉编译（复杂，不推荐）

需要安装大量依赖和配置交叉编译工具链。

### 选项B：使用GitHub Actions自动构建（推荐！）

1. 在GitHub上创建仓库
2. 推送代码
3. 使用GitHub Actions自动为Windows、Linux、macOS构建

我可以帮你创建GitHub Actions配置文件。

### 选项C：让Windows队友自己构建（最简单）

发送项目文件给你的Windows队友，让他在Windows上运行：

```bash
cd zhilian-next/apps/desktop
pnpm install
pnpm tauri build
```

Windows上会生成`.msi`安装包，双击即可安装运行！

---

## 🌐 最简单的方案：部署到Web

**如果你只是想让队友快速看到成果**，最简单的方法是：

### 使用Vercel/Cloudflare Pages（3分钟搞定！）

1. **安装Vercel CLI：**
```bash
pnpm install -g vercel
```

2. **部署：**
```bash
cd /media/kayson/F0001DC5001D9426/Coding/zhilian-next/apps/web
vercel
```

3. **完成！** 会得到一个公网URL，任何人都能访问！

例如：`https://zhilian-next-xxx.vercel.app`

你的队友只需要：
- 打开浏览器
- 访问这个URL
- 立即看到完整效果！

**优势：**
- ✅ 无需安装任何东西
- ✅ 任何设备都能访问（Windows、Mac、手机）
- ✅ 自动HTTPS加密
- ✅ 全球CDN加速
- ✅ 完全免费

---

## 🎯 我的建议

**如果是展示成果给队友：**
→ 使用Vercel部署（最快最简单）

**如果需要真正的桌面应用：**
→ 让Windows队友在Windows系统上构建

**如果想自己构建Windows版本：**
→ 使用GitHub Actions自动构建

---

## 📝 注意事项

1. **数据库连接：** Tauri打包后，需要确保数据库可访问
2. **环境变量：** 打包前设置好所有环境变量
3. **图标：** 可以替换`apps/desktop/src-tauri/icons/`中的图标

---

需要我帮你配置哪种方案？😊

