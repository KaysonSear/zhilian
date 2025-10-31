import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  output: 'export', // 静态导出，用于Tauri打包
  images: {
    unoptimized: true,
  },
  transpilePackages: ['@zhilian/database', '@zhilian/shared'],
};

export default nextConfig;
