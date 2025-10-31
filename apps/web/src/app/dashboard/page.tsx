import { Activity, AlertTriangle, Shield, TrendingUp } from 'lucide-react';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';

export default async function DashboardPage() {
  // 这里将来会从API获取真实数据
  const stats = {
    totalFlows: 125847,
    activeAlerts: 23,
    rulesCount: 156,
    systemHealth: 98.5,
  };

  return (
    <div className="space-y-8">
      <div>
        <h1 className="text-3xl font-bold dark:text-white">控制台总览</h1>
        <p className="text-slate-600 dark:text-slate-400 mt-2">
          实时监控系统安全态势和关键指标
        </p>
      </div>

      {/* Stats Cards */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <StatsCard
          title="总流量记录"
          value={stats.totalFlows.toLocaleString()}
          icon={<Activity className="h-5 w-5" />}
          trend="+12.5%"
          trendUp
        />
        <StatsCard
          title="活跃告警"
          value={stats.activeAlerts.toString()}
          icon={<AlertTriangle className="h-5 w-5" />}
          trend="-8.2%"
          trendUp={false}
        />
        <StatsCard
          title="安全规则"
          value={stats.rulesCount.toString()}
          icon={<Shield className="h-5 w-5" />}
          trend="+3"
          trendUp
        />
        <StatsCard
          title="系统健康度"
          value={`${stats.systemHealth}%`}
          icon={<TrendingUp className="h-5 w-5" />}
          trend="+0.5%"
          trendUp
        />
      </div>

      {/* Charts and Tables */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <Card>
          <CardHeader>
            <CardTitle>最近流量趋势</CardTitle>
            <CardDescription>过去24小时的流量统计</CardDescription>
          </CardHeader>
          <CardContent>
            <div className="h-64 flex items-center justify-center text-slate-400">
              流量趋势图（即将实现）
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle>攻击类型分布</CardTitle>
            <CardDescription>检测到的威胁类型统计</CardDescription>
          </CardHeader>
          <CardContent>
            <div className="h-64 flex items-center justify-center text-slate-400">
              攻击类型分布图（即将实现）
            </div>
          </CardContent>
        </Card>
      </div>

      {/* Recent Alerts */}
      <Card>
        <CardHeader>
          <CardTitle>最新告警</CardTitle>
          <CardDescription>需要关注的最新安全告警</CardDescription>
        </CardHeader>
        <CardContent>
          <div className="space-y-4">
            <AlertItem
              severity="HIGH"
              title="检测到端口扫描行为"
              source="192.168.1.100"
              time="2分钟前"
            />
            <AlertItem
              severity="MEDIUM"
              title="可疑的SSH登录尝试"
              source="203.0.113.42"
              time="15分钟前"
            />
            <AlertItem
              severity="CRITICAL"
              title="SQL注入攻击尝试"
              source="198.51.100.23"
              time="1小时前"
            />
          </div>
        </CardContent>
      </Card>
    </div>
  );
}

function StatsCard({
  title,
  value,
  icon,
  trend,
  trendUp,
}: {
  title: string;
  value: string;
  icon: React.ReactNode;
  trend: string;
  trendUp: boolean;
}) {
  return (
    <Card>
      <CardHeader className="flex flex-row items-center justify-between pb-2">
        <CardTitle className="text-sm font-medium text-slate-600 dark:text-slate-400">
          {title}
        </CardTitle>
        <div className="text-slate-400">{icon}</div>
      </CardHeader>
      <CardContent>
        <div className="text-2xl font-bold dark:text-white">{value}</div>
        <p className={`text-xs mt-1 ${trendUp ? 'text-green-600' : 'text-red-600'}`}>
          {trend} 较昨日
        </p>
      </CardContent>
    </Card>
  );
}

function AlertItem({
  severity,
  title,
  source,
  time,
}: {
  severity: string;
  title: string;
  source: string;
  time: string;
}) {
  const severityColors = {
    CRITICAL: 'bg-red-100 text-red-800 dark:bg-red-900/20 dark:text-red-400',
    HIGH: 'bg-orange-100 text-orange-800 dark:bg-orange-900/20 dark:text-orange-400',
    MEDIUM: 'bg-yellow-100 text-yellow-800 dark:bg-yellow-900/20 dark:text-yellow-400',
    LOW: 'bg-blue-100 text-blue-800 dark:bg-blue-900/20 dark:text-blue-400',
  };

  return (
    <div className="flex items-start justify-between p-4 rounded-lg border border-slate-200 dark:border-slate-700 hover:bg-slate-50 dark:hover:bg-slate-800/50 transition-colors">
      <div className="flex-1">
        <div className="flex items-center space-x-2 mb-1">
          <span
            className={`px-2 py-1 text-xs font-medium rounded ${severityColors[severity as keyof typeof severityColors]}`}
          >
            {severity}
          </span>
          <span className="text-sm font-medium dark:text-white">{title}</span>
        </div>
        <div className="text-sm text-slate-600 dark:text-slate-400">
          来源: {source} · {time}
        </div>
      </div>
      <button className="text-blue-600 hover:text-blue-700 text-sm font-medium">
        查看详情
      </button>
    </div>
  );
}

