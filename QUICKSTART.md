# 快速开始指南

## 前置条件

确保您的系统已安装以下工具：
- Node.js >= 25.0.0
- pnpm >= 10.0.0
- PostgreSQL >= 17.0
- Bun >= 1.3.0 (可选)
- Rust >= 1.91.0 (仅桌面端需要)

## 快速启动

### 1. 克隆或导航到项目目录

```bash
cd /media/kayson/F0001DC5001D9426/Coding/zhilian-next
```

### 2. 安装依赖

```bash
pnpm install
```

### 3. 配置数据库

```bash
# 创建PostgreSQL数据库
sudo -u postgres psql -c "CREATE DATABASE zhilian;"

# 设置数据库URL
cd packages/database
echo 'DATABASE_URL="postgresql://postgres:your_password@localhost:5432/zhilian"' > .env

# 推送数据库schema
pnpm db:push
```

### 4. 配置Web应用环境变量

```bash
cd apps/web
echo 'DATABASE_URL="postgresql://postgres:your_password@localhost:5432/zhilian"
NEXT_PUBLIC_API_URL="http://localhost:3000"' > .env.local
```

### 5. 启动开发服务器

#### 启动所有应用
```bash
cd /media/kayson/F0001DC5001D9426/Coding/zhilian-next
pnpm dev
```

#### 或单独启动Web应用
```bash
pnpm --filter @zhilian/web dev
```

#### 或单独启动移动应用
```bash
pnpm --filter @zhilian/mobile start
```

#### 或单独启动桌面应用
```bash
pnpm --filter @zhilian/desktop tauri dev
```

### 6. 访问应用

- **Web应用**: http://localhost:3000
- **移动应用**: 扫描终端中的二维码或在模拟器中打开
- **桌面应用**: 自动打开桌面窗口

## 开发命令

### Web应用

```bash
# 开发模式
pnpm --filter @zhilian/web dev

# 构建生产版本
pnpm --filter @zhilian/web build

# 启动生产服务器
pnpm --filter @zhilian/web start

# 代码检查
pnpm --filter @zhilian/web lint
```

### 数据库

```bash
cd packages/database

# 生成Prisma客户端
pnpm db:generate

# 推送schema到数据库
pnpm db:push

# 创建迁移
pnpm db:migrate

# 打开Prisma Studio
pnpm db:studio
```

### 移动应用

```bash
# 开发模式
pnpm --filter @zhilian/mobile start

# Android
pnpm --filter @zhilian/mobile android

# iOS (需要macOS)
pnpm --filter @zhilian/mobile ios

# Web版本
pnpm --filter @zhilian/mobile web
```

### 桌面应用

```bash
# 开发模式
pnpm --filter @zhilian/desktop tauri dev

# 构建应用
pnpm --filter @zhilian/desktop tauri build
```

### Monorepo命令

```bash
# 安装所有依赖
pnpm install

# 启动所有应用
pnpm dev

# 构建所有应用
pnpm build

# 代码格式化
pnpm format

# 清理
pnpm clean
```

## 常见问题

### 数据库连接失败

1. 确认PostgreSQL正在运行：
```bash
sudo systemctl status postgresql
```

2. 检查数据库密码是否正确

3. 确认数据库已创建：
```bash
sudo -u postgres psql -l | grep zhilian
```

### Prisma构建错误

如果遇到Prisma构建脚本被忽略的警告：
```bash
pnpm approve-builds @prisma/client @prisma/engines prisma
```

### Tauri依赖问题

在Linux上，确保已安装所需的系统依赖：
```bash
sudo apt install -y \
  libwebkit2gtk-4.1-dev \
  libgtk-3-dev \
  libayatana-appindicator3-dev \
  librsvg2-dev
```

### 端口占用

如果3000端口被占用，可以修改端口：
```bash
# 修改apps/web/package.json中的dev脚本
"dev": "next dev -p 3001"
```

### 移动端React版本警告

这是已知问题，不影响开发。可以忽略peer dependency警告。

## 项目结构说明

```
zhilian-next/
├── apps/           # 应用
│   ├── web/        # Next.js Web应用
│   ├── mobile/     # React Native移动应用
│   └── desktop/    # Tauri桌面应用
├── packages/       # 共享包
│   ├── database/   # Prisma数据库
│   └── shared/     # 共享类型和工具
└── docs/           # 文档
```

## 下一步

1. 阅读 [README.md](./README.md) 了解项目详情
2. 查看 [PROJECT_SUMMARY.md](./PROJECT_SUMMARY.md) 了解技术栈和架构
3. 开始开发新功能！

## 获取帮助

- 查看文档: `./docs`
- 提交Issue: GitHub Issues
- 联系维护者: [联系方式]

---

祝开发愉快！🚀

