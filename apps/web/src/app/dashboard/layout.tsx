import Link from 'next/link';
import {
  LayoutDashboard,
  Activity,
  AlertTriangle,
  Shield,
  Server,
  Settings,
  Menu,
  Map,
} from 'lucide-react';

export default function DashboardLayout({ children }: { children: React.ReactNode }) {
  return (
    <div className="flex h-screen bg-slate-50 dark:bg-slate-900">
      {/* Sidebar */}
      <aside className="w-64 bg-white dark:bg-slate-800 border-r border-slate-200 dark:border-slate-700">
        <div className="flex items-center space-x-3 p-6 border-b border-slate-200 dark:border-slate-700">
          <div className="p-2 bg-gradient-to-br from-blue-500 to-purple-600 rounded-lg">
            <Shield className="h-5 w-5 text-white" />
          </div>
          <span className="font-semibold text-lg dark:text-white">智链平台</span>
        </div>

        <nav className="p-4 space-y-2">
          <NavLink href="/dashboard" icon={<LayoutDashboard className="h-5 w-5" />}>
            总览
          </NavLink>
          <NavLink href="/dashboard/map" icon={<Map className="h-5 w-5" />}>
            流量地图
          </NavLink>
          <NavLink href="/dashboard/flows" icon={<Activity className="h-5 w-5" />}>
            流量分析
          </NavLink>
          <NavLink href="/dashboard/alerts" icon={<AlertTriangle className="h-5 w-5" />}>
            威胁告警
          </NavLink>
          <NavLink href="/dashboard/rules" icon={<Shield className="h-5 w-5" />}>
            规则管理
          </NavLink>
          <NavLink href="/dashboard/hosts" icon={<Server className="h-5 w-5" />}>
            主机监控
          </NavLink>
          <NavLink href="/dashboard/settings" icon={<Settings className="h-5 w-5" />}>
            系统设置
          </NavLink>
        </nav>
      </aside>

      {/* Main Content */}
      <main className="flex-1 overflow-auto">
        <div className="p-8">{children}</div>
      </main>
    </div>
  );
}

function NavLink({
  href,
  icon,
  children,
}: {
  href: string;
  icon: React.ReactNode;
  children: React.ReactNode;
}) {
  return (
    <Link
      href={href}
      className="flex items-center space-x-3 px-4 py-3 rounded-lg text-slate-700 dark:text-slate-300 hover:bg-slate-100 dark:hover:bg-slate-700 transition-colors"
    >
      {icon}
      <span>{children}</span>
    </Link>
  );
}

