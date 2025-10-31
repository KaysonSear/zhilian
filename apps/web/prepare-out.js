const fs = require('fs');
const path = require('path');

const nextDir = path.join(__dirname, '.next');
const outDir = path.join(__dirname, 'out');

console.log('📦 Preparing static export...');

// 创建out目录
if (!fs.existsSync(outDir)) {
  fs.mkdirSync(outDir, { recursive: true });
  console.log('✓ Created out directory');
}

// 复制.next内容到out
if (fs.existsSync(nextDir)) {
  fs.cpSync(nextDir, outDir, { recursive: true, force: true });
  console.log('✓ Copied .next to out/');
} else {
  console.error('✗ .next directory not found!');
  process.exit(1);
}

console.log('✓ Static export prepared successfully!');

