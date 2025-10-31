import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  output: 'export', // 静态导出
  images: {
    unoptimized: true,
  },
  transpilePackages: ['@zhilian/database', '@zhilian/shared'],
  trailingSlash: true, // 确保生成正确的静态路径
};

export default nextConfig;
