# 智链分析溯源平台 (现代化版本)

基于最新TypeScript技术栈重构的网络安全智能分析及溯源平台。

## 技术栈

### 前端
- **Next.js 15** - React框架，支持App Router、服务端组件和流式渲染
- **React 19** - 最新版本，支持Server Components
- **TypeScript 5.7** - 类型安全
- **Tailwind CSS 4** - 现代化CSS框架
- **shadcn/ui** - 高质量UI组件库

### 移动端
- **React Native** - 启用Fabric和TurboModules
- **Expo SDK 54** - 支持'use dom'，实现Web与原生组件复用

### 桌面端
- **Tauri 2.x** - 轻量级桌面应用框架

### 服务端
- **Node.js 25** / **Bun 1.3** - 高性能JavaScript/TypeScript运行时
- **Next.js API Routes** - 边缘函数支持

### 数据层
- **PostgreSQL 17** - 关系型数据库
- **Prisma 6** - 类型安全的ORM
- **Redis** - 缓存和实时数据

### 边缘部署
- **Cloudflare Workers** - 边缘计算
- **Cloudflare Pages Functions** - 静态网站 + 动态API

## 项目结构

```
zhilian-next/
├── apps/
│   ├── web/           # Next.js 网页应用
│   ├── mobile/        # React Native + Expo 移动应用
│   └── desktop/       # Tauri 桌面应用
├── packages/
│   ├── ui/            # 共享UI组件
│   ├── database/      # Prisma schema和数据库工具
│   ├── shared/        # 共享类型、工具和常量
│   └── config/        # 共享配置文件
└── turbo.json         # Turborepo配置
```

## 核心功能

1. **流量分析** - 网络流量实时监控和深度分析
2. **主机监控** - 系统资源监控和进程管理
3. **威胁告警** - 智能威胁检测和告警系统
4. **规则管理** - 安全规则配置和管理
5. **区块链存证** - 使用PostgreSQL实现不可篡改日志
6. **实时数据大屏** - 数据可视化展示

## 开发环境要求

- Node.js >= 25.0.0
- pnpm >= 10.0.0
- Bun >= 1.3.0 (可选)
- PostgreSQL >= 17.0
- Rust >= 1.91.0 (桌面端需要)

## 快速开始

```bash
# 安装依赖
pnpm install

# 配置数据库
cd packages/database
cp .env.example .env
# 编辑 .env 文件，设置数据库连接
pnpm db:push

# 启动开发服务器
pnpm dev

# 构建生产版本
pnpm build
```

## 环境变量

创建 `.env` 文件：

```env
# 数据库
DATABASE_URL="postgresql://user:password@localhost:5432/zhilian"

# Redis
REDIS_URL="redis://localhost:6379"

# 应用配置
NEXT_PUBLIC_API_URL="http://localhost:3000"
```

## 部署

### Cloudflare Pages
```bash
pnpm build
npx wrangler pages deploy apps/web/out
```

### Docker
```bash
docker-compose up -d
```

## 许可证

MIT

