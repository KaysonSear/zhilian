'use client';

import { useEffect, useState } from 'react';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Activity, AlertTriangle } from 'lucide-react';

interface Metrics {
  timestamp: Date;
  cpuUsage: number;
  memoryUsage: number;
  diskUsage: number;
  networkIn: number;
  networkOut: number;
}

interface Alert {
  id: string;
  timestamp: Date;
  severity: string;
  alertType: string;
  sourceIp: string;
  title: string;
  description: string;
  status: string;
}

export default function RealtimePage() {
  const [metrics, setMetrics] = useState<Metrics | null>(null);
  const [alerts, setAlerts] = useState<Alert[]>([]);
  const [connected, setConnected] = useState(false);

  useEffect(() => {
    // 连接主机指标流
    const metricsSource = new EventSource('/api/stream/metrics');

    metricsSource.onopen = () => {
      console.log('[Metrics] Connected');
      setConnected(true);
    };

    metricsSource.onmessage = (event) => {
      try {
        const data = JSON.parse(event.data);
        if (data.type === 'metrics') {
          setMetrics(data.data);
        }
      } catch (error) {
        console.error('[Metrics] Parse error:', error);
      }
    };

    metricsSource.onerror = () => {
      console.error('[Metrics] Connection error');
      setConnected(false);
    };

    // 连接告警流
    const alertsSource = new EventSource('/api/stream/alerts');

    alertsSource.onmessage = (event) => {
      try {
        const data = JSON.parse(event.data);
        if (data.type === 'alert') {
          setAlerts((prev) => [data.data, ...prev].slice(0, 10));
        }
      } catch (error) {
        console.error('[Alerts] Parse error:', error);
      }
    };

    return () => {
      metricsSource.close();
      alertsSource.close();
    };
  }, []);

  return (
    <div className="space-y-8">
      <div>
        <h1 className="text-3xl font-bold dark:text-white">实时监控</h1>
        <p className="text-slate-600 dark:text-slate-400 mt-2">
          基于SSE的实时数据推送
          {connected && (
            <span className="ml-2 inline-flex items-center px-2 py-1 text-xs font-medium rounded-full bg-green-100 text-green-800 dark:bg-green-900/20 dark:text-green-400">
              <span className="w-2 h-2 bg-green-500 rounded-full mr-1 animate-pulse"></span>
              已连接
            </span>
          )}
        </p>
      </div>

      {/* 主机指标 */}
      <Card>
        <CardHeader>
          <div className="flex items-center justify-between">
            <div>
              <CardTitle className="flex items-center">
                <Activity className="h-5 w-5 mr-2" />
                主机性能指标
              </CardTitle>
              <CardDescription>实时系统资源使用情况</CardDescription>
            </div>
          </div>
        </CardHeader>
        <CardContent>
          {metrics ? (
            <div className="grid grid-cols-2 md:grid-cols-5 gap-4">
              <MetricCard label="CPU使用率" value={metrics.cpuUsage.toFixed(1)} unit="%" />
              <MetricCard label="内存使用率" value={metrics.memoryUsage.toFixed(1)} unit="%" />
              <MetricCard label="磁盘使用率" value={metrics.diskUsage.toFixed(1)} unit="%" />
              <MetricCard
                label="网络入流量"
                value={metrics.networkIn.toFixed(0)}
                unit="KB/s"
              />
              <MetricCard
                label="网络出流量"
                value={metrics.networkOut.toFixed(0)}
                unit="KB/s"
              />
            </div>
          ) : (
            <div className="text-center py-8 text-slate-500">等待数据...</div>
          )}
        </CardContent>
      </Card>

      {/* 实时告警 */}
      <Card>
        <CardHeader>
          <CardTitle className="flex items-center">
            <AlertTriangle className="h-5 w-5 mr-2" />
            实时告警流
          </CardTitle>
          <CardDescription>最新的安全威胁告警</CardDescription>
        </CardHeader>
        <CardContent>
          {alerts.length > 0 ? (
            <div className="space-y-3">
              {alerts.map((alert) => (
                <AlertItem key={alert.id} alert={alert} />
              ))}
            </div>
          ) : (
            <div className="text-center py-8 text-slate-500">暂无新告警</div>
          )}
        </CardContent>
      </Card>
    </div>
  );
}

function MetricCard({
  label,
  value,
  unit,
}: {
  label: string;
  value: string;
  unit: string;
}) {
  return (
    <div className="p-4 rounded-lg border border-slate-200 dark:border-slate-700 bg-slate-50 dark:bg-slate-800/50">
      <div className="text-sm text-slate-600 dark:text-slate-400 mb-1">{label}</div>
      <div className="text-2xl font-bold dark:text-white">
        {value}
        <span className="text-sm font-normal ml-1">{unit}</span>
      </div>
    </div>
  );
}

function AlertItem({ alert }: { alert: Alert }) {
  const severityColors = {
    CRITICAL: 'bg-red-100 text-red-800 dark:bg-red-900/20 dark:text-red-400',
    HIGH: 'bg-orange-100 text-orange-800 dark:bg-orange-900/20 dark:text-orange-400',
    MEDIUM: 'bg-yellow-100 text-yellow-800 dark:bg-yellow-900/20 dark:text-yellow-400',
    LOW: 'bg-blue-100 text-blue-800 dark:bg-blue-900/20 dark:text-blue-400',
  };

  return (
    <div className="flex items-start justify-between p-4 rounded-lg border border-slate-200 dark:border-slate-700 hover:bg-slate-50 dark:hover:bg-slate-800/50 transition-colors animate-in fade-in slide-in-from-right duration-300">
      <div className="flex-1">
        <div className="flex items-center space-x-2 mb-1">
          <span
            className={`px-2 py-1 text-xs font-medium rounded ${severityColors[alert.severity as keyof typeof severityColors]}`}
          >
            {alert.severity}
          </span>
          <span className="text-sm font-medium dark:text-white">{alert.title}</span>
        </div>
        <div className="text-sm text-slate-600 dark:text-slate-400">
          类型: {alert.alertType} · 来源: {alert.sourceIp}
        </div>
        <div className="text-xs text-slate-500 mt-1">
          {new Date(alert.timestamp).toLocaleString('zh-CN')}
        </div>
      </div>
    </div>
  );
}

