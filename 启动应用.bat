@echo off
chcp 65001 >nul
title 智链分析溯源平台

echo ================================================
echo      智链分析溯源平台 - Windows启动脚本
echo ================================================
echo.

:: 进入项目目录
cd /d "%~dp0"

echo [信息] 项目目录：%CD%
echo.

echo [启动] 正在启动开发服务器...
echo.
echo [提示] 请等待20-30秒，然后访问：
echo.
echo      👉 http://localhost:3000
echo.
echo [页面]
echo      - 首页: http://localhost:3000
echo      - 控制台: http://localhost:3000/dashboard
echo      - 流量地图: http://localhost:3000/dashboard/map
echo.
echo [提示] 按 Ctrl+C 可以停止服务器
echo.
echo ================================================
echo.

:: 启动
pnpm --filter @zhilian/web dev

pause

