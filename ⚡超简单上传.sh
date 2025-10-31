#!/bin/bash
cd /media/kayson/F0001DC5001D9426/Coding/zhilian-next
git init
git add .
git commit -m "智链安全平台 - 现代化重构版 (Next.js + React + Tauri)"
git remote remove origin 2>/dev/null
git remote add origin https://github.com/KaysonSear/zhilian.git
echo ""
echo "=========================================="
echo "  准备推送到GitHub..."
echo "=========================================="
echo ""
echo "⚠️  输入密码时，使用 Personal Access Token"
echo "   获取地址: https://github.com/settings/tokens"
echo ""
git push -f origin main

if [ $? -eq 0 ]; then
    echo ""
    echo "✅ 成功！访问这里查看构建进度："
    echo "   https://github.com/KaysonSear/zhilian/actions"
    echo ""
    echo "⏱️  等待15-20分钟后下载 .msi 安装包"
fi

