import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  output: 'export', // 静态导出到out目录
  images: {
    unoptimized: true,
  },
  transpilePackages: ['@zhilian/database', '@zhilian/shared'],
};

export default nextConfig;
