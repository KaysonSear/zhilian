import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  output: 'export', // 启用静态导出，用于Tauri打包
  images: {
    unoptimized: true, // 静态导出需要禁用图片优化
  },
  distDir: process.env.BUILD_FOR_TAURI ? '../desktop/dist' : '.next',
};

export default nextConfig;
