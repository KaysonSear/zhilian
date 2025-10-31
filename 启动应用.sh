#!/bin/bash

# 智链分析溯源平台 - 一键启动脚本

echo "================================================"
echo "     智链分析溯源平台 - 启动脚本"
echo "================================================"
echo ""

# 设置颜色
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# 进入项目目录
cd "$(dirname "$0")"

echo -e "${BLUE}📍 项目目录：${NC}$(pwd)"
echo ""

# 检查PostgreSQL
echo -e "${YELLOW}🔍 检查PostgreSQL...${NC}"
if systemctl is-active --quiet postgresql; then
    echo -e "${GREEN}✓ PostgreSQL 正在运行${NC}"
else
    echo -e "${YELLOW}⚠ PostgreSQL 未运行，正在启动...${NC}"
    echo "760810" | sudo -S systemctl start postgresql
    sleep 2
fi
echo ""

# 检查数据库
echo -e "${YELLOW}🔍 检查数据库...${NC}"
if sudo -u postgres psql -lqt | cut -d \| -f 1 | grep -qw zhilian; then
    echo -e "${GREEN}✓ 数据库 zhilian 已存在${NC}"
else
    echo -e "${YELLOW}⚠ 创建数据库 zhilian...${NC}"
    echo "760810" | sudo -S -u postgres psql -c "CREATE DATABASE zhilian;"
fi
echo ""

# 启动开发服务器
echo -e "${BLUE}🚀 启动开发服务器...${NC}"
echo ""
echo -e "${GREEN}请等待20-30秒，然后访问：${NC}"
echo -e "${GREEN}👉 http://localhost:3000${NC}"
echo ""
echo -e "${YELLOW}提示：${NC}"
echo -e "  - 按 ${YELLOW}Ctrl+C${NC} 可以停止服务器"
echo -e "  - 首页: http://localhost:3000"
echo -e "  - 控制台: http://localhost:3000/dashboard"
echo -e "  - 流量地图: http://localhost:3000/dashboard/map"
echo ""
echo "================================================"
echo ""

# 启动
pnpm --filter @zhilian/web dev

