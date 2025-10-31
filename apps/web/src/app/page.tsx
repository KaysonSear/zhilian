import { Activity, Shield, TrendingUp, AlertTriangle, Server, Network } from 'lucide-react';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import Link from 'next/link';

export default function HomePage() {
  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-900 via-slate-800 to-slate-900">
      {/* Header */}
      <header className="border-b border-slate-700/50 backdrop-blur-sm bg-slate-900/50">
        <div className="container mx-auto px-4 py-4">
          <div className="flex items-center justify-between">
            <div className="flex items-center space-x-3">
              <div className="p-2 bg-gradient-to-br from-blue-500 to-purple-600 rounded-lg">
                <Shield className="h-6 w-6 text-white" />
              </div>
              <h1 className="text-2xl font-bold bg-gradient-to-r from-blue-400 to-purple-400 bg-clip-text text-transparent">
                智链分析溯源平台
              </h1>
            </div>
            <nav className="flex space-x-4">
              <Link 
                href="/dashboard" 
                className="px-4 py-2 rounded-lg bg-blue-600 hover:bg-blue-700 text-white transition-colors"
              >
                进入控制台
              </Link>
            </nav>
          </div>
        </div>
      </header>

      {/* Hero Section */}
      <div className="container mx-auto px-4 py-20">
        <div className="text-center mb-16">
          <h2 className="text-5xl font-bold text-white mb-4">
            网络安全智能分析及溯源系统
          </h2>
          <p className="text-xl text-slate-300 max-w-2xl mx-auto">
            基于最新TypeScript技术栈，提供实时威胁检测、流量分析和安全溯源能力
          </p>
        </div>

        {/* Feature Cards */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-16">
          <Card className="bg-slate-800/50 border-slate-700 hover:border-blue-500/50 transition-all hover:scale-105">
            <CardHeader>
              <div className="p-3 bg-blue-500/10 rounded-lg w-fit mb-2">
                <Activity className="h-6 w-6 text-blue-400" />
              </div>
              <CardTitle className="text-white">实时流量分析</CardTitle>
              <CardDescription className="text-slate-400">
                深度学习模型实时分析网络流量，识别异常行为和潜在威胁
              </CardDescription>
            </CardHeader>
          </Card>

          <Card className="bg-slate-800/50 border-slate-700 hover:border-purple-500/50 transition-all hover:scale-105">
            <CardHeader>
              <div className="p-3 bg-purple-500/10 rounded-lg w-fit mb-2">
                <AlertTriangle className="h-6 w-6 text-purple-400" />
              </div>
              <CardTitle className="text-white">智能威胁告警</CardTitle>
              <CardDescription className="text-slate-400">
                多维度威胁检测引擎，及时发现和预警各类网络安全威胁
              </CardDescription>
            </CardHeader>
          </Card>

          <Card className="bg-slate-800/50 border-slate-700 hover:border-green-500/50 transition-all hover:scale-105">
            <CardHeader>
              <div className="p-3 bg-green-500/10 rounded-lg w-fit mb-2">
                <Server className="h-6 w-6 text-green-400" />
              </div>
              <CardTitle className="text-white">主机监控</CardTitle>
              <CardDescription className="text-slate-400">
                实时监控主机性能指标和进程状态，保障系统稳定运行
              </CardDescription>
            </CardHeader>
          </Card>

          <Card className="bg-slate-800/50 border-slate-700 hover:border-orange-500/50 transition-all hover:scale-105">
            <CardHeader>
              <div className="p-3 bg-orange-500/10 rounded-lg w-fit mb-2">
                <Shield className="h-6 w-6 text-orange-400" />
              </div>
              <CardTitle className="text-white">规则管理</CardTitle>
              <CardDescription className="text-slate-400">
                灵活配置安全规则，支持多种匹配模式和处置策略
              </CardDescription>
            </CardHeader>
          </Card>

          <Card className="bg-slate-800/50 border-slate-700 hover:border-cyan-500/50 transition-all hover:scale-105">
            <CardHeader>
              <div className="p-3 bg-cyan-500/10 rounded-lg w-fit mb-2">
                <Network className="h-6 w-6 text-cyan-400" />
              </div>
              <CardTitle className="text-white">可视化大屏</CardTitle>
              <CardDescription className="text-slate-400">
                炫酷的数据可视化展示，实时呈现安全态势和关键指标
              </CardDescription>
            </CardHeader>
          </Card>

          <Card className="bg-slate-800/50 border-slate-700 hover:border-pink-500/50 transition-all hover:scale-105">
            <CardHeader>
              <div className="p-3 bg-pink-500/10 rounded-lg w-fit mb-2">
                <TrendingUp className="h-6 w-6 text-pink-400" />
              </div>
              <CardTitle className="text-white">安全溯源</CardTitle>
              <CardDescription className="text-slate-400">
                完整的审计日志和溯源能力，追踪安全事件全过程
              </CardDescription>
            </CardHeader>
          </Card>
        </div>

        {/* Tech Stack */}
        <div className="text-center">
          <h3 className="text-2xl font-bold text-white mb-8">现代化技术栈</h3>
          <div className="flex flex-wrap justify-center gap-4">
            {['Next.js 16', 'React 19', 'TypeScript', 'Prisma', 'PostgreSQL', 'Tailwind CSS', 'React Native', 'Tauri'].map((tech) => (
              <span
                key={tech}
                className="px-4 py-2 bg-slate-800/50 border border-slate-700 rounded-lg text-slate-300 hover:border-blue-500/50 transition-colors"
              >
                {tech}
              </span>
            ))}
          </div>
        </div>
      </div>

      {/* Footer */}
      <footer className="border-t border-slate-700/50 py-8">
        <div className="container mx-auto px-4 text-center text-slate-400">
          <p>© 2025 智链分析溯源平台. 基于最新技术栈重构.</p>
        </div>
      </footer>
    </div>
  );
}
