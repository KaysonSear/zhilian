#!/bin/bash

echo "=========================================="
echo "  🚀 智链平台 - 一键上传到GitHub"
echo "=========================================="
echo ""

# 切换到项目目录
cd /media/kayson/F0001DC5001D9426/Coding/zhilian-next

# 检查git是否已初始化
if [ ! -d ".git" ]; then
    echo "📦 初始化Git仓库..."
    git init
fi

echo "📝 添加所有文件..."
git add .

echo "💾 提交更改..."
git commit -m "✨ 智链安全平台 - 现代化重构版

🎨 新特性：
- 真实中国地图流量态势展示（ECharts + 阿里云地图）
- 完整的网络安全监控平台（Next.js + React + TypeScript）
- Tauri桌面应用支持（自动打包Windows .msi）
- GitHub Actions自动构建流程

🛠️ 技术栈：
- 前端: Next.js 16 + React 19 + Tailwind CSS 4
- 桌面: Tauri 2.2 (Rust)
- 数据: PostgreSQL + Prisma
- 构建: GitHub Actions + pnpm + Turborepo

📊 功能模块：
- 实时流量监控与分析
- 威胁告警系统
- 规则管理
- 主机性能监控
- 全国流量态势地图
"

echo ""
echo "🔗 配置远程仓库..."

# 检查是否已有remote
if git remote | grep -q "origin"; then
    echo "   已存在origin，删除旧配置..."
    git remote remove origin
fi

echo "   添加你的GitHub仓库: https://github.com/KaysonSear/zhilian"
git remote add origin https://github.com/KaysonSear/zhilian.git

echo ""
echo "=========================================="
echo "  ⚠️  重要提示"
echo "=========================================="
echo ""
echo "接下来将推送代码到GitHub并覆盖原有内容。"
echo ""
echo "你需要输入GitHub认证信息："
echo "  用户名: KaysonSear"
echo "  密码: 使用 Personal Access Token (不是GitHub密码!)"
echo ""
echo "📌 如何获取Token："
echo "  1. 访问: https://github.com/settings/tokens"
echo "  2. 点击 'Generate new token (classic)'"
echo "  3. 勾选 'repo' 权限"
echo "  4. 复制生成的token"
echo ""
read -p "按回车键继续推送，或按 Ctrl+C 取消..." 

echo ""
echo "🚀 推送代码到GitHub (强制覆盖)..."
echo ""

# 强制推送，覆盖远程仓库
git push -f origin main

if [ $? -eq 0 ]; then
    echo ""
    echo "=========================================="
    echo "  ✅ 成功！代码已上传到GitHub"
    echo "=========================================="
    echo ""
    echo "📍 你的仓库地址："
    echo "   https://github.com/KaysonSear/zhilian"
    echo ""
    echo "🤖 GitHub Actions正在自动构建中..."
    echo "   查看构建进度:"
    echo "   https://github.com/KaysonSear/zhilian/actions"
    echo ""
    echo "⏱️  预计15-20分钟后构建完成"
    echo ""
    echo "📦 构建完成后下载安装包："
    echo "   1. 访问上面的Actions链接"
    echo "   2. 点击最新的工作流"
    echo "   3. 滚动到底部下载 'windows-installer'"
    echo "   4. 解压得到 .msi 文件"
    echo "   5. 发给同事双击安装！"
    echo ""
    echo "🎉 完成！"
    echo ""
else
    echo ""
    echo "❌ 推送失败！"
    echo ""
    echo "可能的原因："
    echo "  1. GitHub Token权限不足"
    echo "  2. 网络连接问题"
    echo "  3. 仓库名称错误"
    echo ""
    echo "💡 解决方案："
    echo "  1. 确保使用Personal Access Token (不是密码)"
    echo "  2. Token需要有 'repo' 完整权限"
    echo "  3. 检查网络连接"
    echo ""
fi

