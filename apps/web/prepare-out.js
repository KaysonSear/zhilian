const fs = require('fs');
const path = require('path');

const nextDir = path.join(__dirname, '.next');
const outDir = path.join(__dirname, 'out');

console.log('📦 Preparing static export...');
console.log('   Current directory:', __dirname);
console.log('   Next.js dir:', nextDir);
console.log('   Output dir:', outDir);

// 创建out目录
if (!fs.existsSync(outDir)) {
  fs.mkdirSync(outDir, { recursive: true });
  console.log('✓ Created out directory');
}

// 复制.next内容到out
if (fs.existsSync(nextDir)) {
  fs.cpSync(nextDir, outDir, { recursive: true, force: true });
  console.log('✓ Copied .next to out/');
  
  // 验证out目录内容
  const files = fs.readdirSync(outDir);
  console.log('✓ Out directory contains:', files.length, 'items');
  console.log('   Full path:', path.resolve(outDir));
} else {
  console.error('✗ .next directory not found!');
  process.exit(1);
}

console.log('✓ Static export prepared successfully!');

