const fs = require('fs');
const path = require('path');

const nextDir = path.join(__dirname, '.next');
const outDir = path.join(__dirname, 'out');

console.log('📦 Preparing static export...');
console.log('   Current directory:', __dirname);
console.log('   Next.js dir:', nextDir);
console.log('   Output dir:', outDir);

// 检查.next目录结构
if (fs.existsSync(nextDir)) {
  console.log('✓ .next directory found');
  
  // 检查是否有static目录（Next.js构建产物）
  const staticDir = path.join(nextDir, 'static');
  const serverDir = path.join(nextDir, 'server');
  
  console.log('   Has static dir:', fs.existsSync(staticDir));
  console.log('   Has server dir:', fs.existsSync(serverDir));
  
  // 如果server/app目录存在，说明使用了App Router
  const appPagesDir = path.join(serverDir, 'app');
  if (fs.existsSync(appPagesDir)) {
    console.log('   Detected App Router structure');
    
    // 创建out目录
    if (fs.existsSync(outDir)) {
      fs.rmSync(outDir, { recursive: true, force: true });
    }
    fs.mkdirSync(outDir, { recursive: true });
    
    // 复制static目录
    if (fs.existsSync(staticDir)) {
      fs.cpSync(staticDir, path.join(outDir, '_next', 'static'), { recursive: true });
      console.log('✓ Copied static assets');
    }
    
    // 复制server/app的静态HTML文件
    const copyHtmlFiles = (src, dest) => {
      if (!fs.existsSync(src)) return;
      
      const items = fs.readdirSync(src, { withFileTypes: true });
      for (const item of items) {
        const srcPath = path.join(src, item.name);
        const destPath = path.join(dest, item.name);
        
        if (item.isDirectory()) {
          fs.mkdirSync(destPath, { recursive: true });
          copyHtmlFiles(srcPath, destPath);
        } else if (item.name.endsWith('.html')) {
          fs.copyFileSync(srcPath, destPath);
          console.log('   Copied:', item.name);
        }
      }
    };
    
    copyHtmlFiles(appPagesDir, outDir);
    
    console.log('✓ Static export prepared successfully!');
    console.log('   Output:', path.resolve(outDir));
    
    // 验证out目录内容
    const files = fs.readdirSync(outDir);
    console.log('✓ Out directory contains:', files.length, 'items');
  } else {
    console.error('✗ Unexpected .next structure');
    console.log('   Falling back to full copy...');
    
    // 备用方案：完整复制
    if (fs.existsSync(outDir)) {
      fs.rmSync(outDir, { recursive: true, force: true });
    }
    fs.cpSync(nextDir, outDir, { recursive: true, force: true });
    console.log('✓ Copied entire .next to out/');
  }
} else {
  console.error('✗ .next directory not found!');
  console.error('   Expected at:', nextDir);
  process.exit(1);
}
