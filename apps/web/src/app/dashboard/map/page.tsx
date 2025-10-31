'use client';

import { useEffect, useRef, useState } from 'react';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Map as MapIcon, TrendingUp, AlertCircle } from 'lucide-react';

// 中国主要城市的真实经纬度坐标
const cities = [
  { name: '北京', lon: 116.4074, lat: 39.9042 },
  { name: '上海', lon: 121.4737, lat: 31.2304 },
  { name: '广州', lon: 113.2644, lat: 23.1291 },
  { name: '深圳', lon: 114.0579, lat: 22.5431 },
  { name: '杭州', lon: 120.1551, lat: 30.2741 },
  { name: '成都', lon: 104.0668, lat: 30.5728 },
  { name: '武汉', lon: 114.3055, lat: 30.5931 },
  { name: '西安', lon: 108.9398, lat: 34.3416 },
  { name: '南京', lon: 118.7969, lat: 32.0603 },
  { name: '重庆', lon: 106.5516, lat: 29.5630 },
];

// 生成流量连接数据
const generateConnections = () => {
  const connections = [];
  for (let i = 0; i < 15; i++) {
    const from = cities[Math.floor(Math.random() * cities.length)];
    let to = cities[Math.floor(Math.random() * cities.length)];
    while (to === from) {
      to = cities[Math.floor(Math.random() * cities.length)];
    }
    connections.push({
      from,
      to,
      value: Math.floor(Math.random() * 100) + 20,
      id: i,
    });
  }
  return connections;
};

export default function MapPage() {
  const chartRef = useRef<HTMLDivElement>(null);
  const [connections, setConnections] = useState(generateConnections());
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    if (!chartRef.current) return;

    // 动态导入echarts（仅在客户端）
    import('echarts').then((echarts) => {
      const chart = echarts.init(chartRef.current!);
      
      // 加载中国地图数据
      fetch('https://geo.datav.aliyun.com/areas_v3/bound/100000_full.json')
        .then(response => response.json())
        .then(geoJson => {
          echarts.registerMap('china', geoJson);
          
          // 准备散点数据
          const scatterData = cities.map(city => ({
            name: city.name,
            value: [city.lon, city.lat, Math.floor(Math.random() * 100) + 50],
          }));

          // 准备连线数据
          const linesData = connections.map(conn => ({
            fromName: conn.from.name,
            toName: conn.to.name,
            coords: [
              [conn.from.lon, conn.from.lat],
              [conn.to.lon, conn.to.lat],
            ],
            value: conn.value,
          }));

          const option = {
            backgroundColor: 'transparent',
            tooltip: {
              trigger: 'item',
              backgroundColor: 'rgba(0,0,0,0.8)',
              borderColor: '#3b82f6',
              borderWidth: 1,
              textStyle: {
                color: '#fff',
              },
              formatter: function(params: any) {
                if (params.seriesType === 'scatter') {
                  return `${params.name}<br/>流量: ${params.value[2]} GB`;
                } else if (params.seriesType === 'lines') {
                  return `${params.data.fromName} → ${params.data.toName}<br/>流量: ${params.data.value} GB`;
                }
                return params.name;
              }
            },
            geo: {
              map: 'china',
              roam: true,
              zoom: 1.2,
              center: [105, 36],
              emphasis: {
                focus: 'self',
                itemStyle: {
                  areaColor: '#2563eb',
                },
                label: {
                  show: true,
                  color: '#fff',
                }
              },
              itemStyle: {
                areaColor: '#1e3a8a',
                borderColor: '#3b82f6',
                borderWidth: 1.5,
                shadowColor: 'rgba(59, 130, 246, 0.5)',
                shadowBlur: 10,
              },
              label: {
                show: false,
                color: '#fff',
              },
            },
            series: [
              {
                name: '流量节点',
                type: 'scatter',
                coordinateSystem: 'geo',
                data: scatterData,
                symbolSize: (val: number[]) => Math.max(8, val[2] / 8),
                label: {
                  show: true,
                  formatter: '{b}',
                  position: 'right',
                  color: '#fff',
                  fontSize: 11,
                  fontWeight: 'bold',
                  backgroundColor: 'rgba(0, 0, 0, 0.5)',
                  padding: [2, 4],
                  borderRadius: 3,
                },
                itemStyle: {
                  color: '#60a5fa',
                  shadowBlur: 15,
                  shadowColor: '#60a5fa',
                },
                emphasis: {
                  label: {
                    show: true,
                    fontSize: 14,
                  },
                  itemStyle: {
                    color: '#fbbf24',
                    shadowBlur: 20,
                    shadowColor: '#fbbf24',
                  },
                },
              },
              {
                name: '流量线路',
                type: 'lines',
                coordinateSystem: 'geo',
                data: linesData,
                lineStyle: {
                  color: (params: any) => {
                    return params.data.value > 80 ? '#ef4444' : '#f59e0b';
                  },
                  opacity: 0.7,
                  curveness: 0.3,
                  width: (params: any) => {
                    return Math.max(1, params.data.value / 40);
                  },
                },
                effect: {
                  show: true,
                  period: 4,
                  trailLength: 0.1,
                  symbolSize: 6,
                  symbol: 'arrow',
                  color: (params: any) => {
                    return params.data.value > 80 ? '#ef4444' : '#fbbf24';
                  },
                },
                emphasis: {
                  lineStyle: {
                    width: 4,
                    opacity: 1,
                  },
                },
              },
            ],
          };

          chart.setOption(option);
          setIsLoading(false);

          // 窗口大小变化时重新渲染
          const handleResize = () => {
            chart.resize();
          };
          window.addEventListener('resize', handleResize);

          return () => {
            window.removeEventListener('resize', handleResize);
            chart.dispose();
          };
        })
        .catch((error) => {
          console.error('地图加载失败:', error);
          setIsLoading(false);
        });
    });

    // 每5秒更新一次连接数据
    const interval = setInterval(() => {
      setConnections(generateConnections());
    }, 5000);

    return () => clearInterval(interval);
  }, [connections]);

  const totalTraffic = connections.reduce((sum, conn) => sum + conn.value, 0);
  const abnormalCount = connections.filter((conn) => conn.value > 80).length;

  return (
    <div className="space-y-6">
      {/* 页面标题 */}
      <div>
        <h1 className="text-3xl font-bold dark:text-white flex items-center">
          <MapIcon className="mr-3 h-8 w-8 text-blue-500" />
          全国流量态势地图
        </h1>
        <p className="text-slate-600 dark:text-slate-400 mt-2">
          基于真实中国地图的网络流量实时监控 · 数据每5秒自动更新
        </p>
      </div>

      {/* 统计卡片 */}
      <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
        <Card>
          <CardHeader className="pb-2">
            <CardDescription>监控城市</CardDescription>
          </CardHeader>
          <CardContent>
            <div className="text-3xl font-bold text-blue-600 dark:text-blue-400">
              {cities.length}
            </div>
            <p className="text-xs text-slate-500 mt-1">个主要节点</p>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="pb-2">
            <CardDescription>活跃连接</CardDescription>
          </CardHeader>
          <CardContent>
            <div className="text-3xl font-bold text-green-600 dark:text-green-400">
              {connections.length}
            </div>
            <p className="text-xs text-slate-500 mt-1">条实时流量</p>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="pb-2">
            <CardDescription>总流量</CardDescription>
          </CardHeader>
          <CardContent>
            <div className="text-3xl font-bold text-purple-600 dark:text-purple-400 flex items-center">
              {totalTraffic}
              <span className="text-sm ml-2">GB</span>
            </div>
            <p className="text-xs text-slate-500 mt-1 flex items-center">
              <TrendingUp className="h-3 w-3 mr-1" />
              实时统计
            </p>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="pb-2">
            <CardDescription>异常流量</CardDescription>
          </CardHeader>
          <CardContent>
            <div className="text-3xl font-bold text-red-600 dark:text-red-400 flex items-center">
              {abnormalCount}
              <AlertCircle className="h-5 w-5 ml-2" />
            </div>
            <p className="text-xs text-slate-500 mt-1">条异常连接</p>
          </CardContent>
        </Card>
      </div>

      {/* 地图容器 */}
      <Card className="bg-gradient-to-br from-slate-900 via-blue-900 to-slate-900">
        <CardHeader>
          <CardTitle className="text-white flex items-center">
            <MapIcon className="mr-2 h-5 w-5" />
            实时流量态势图 - 中国地图
          </CardTitle>
          <CardDescription className="text-slate-300">
            🔵 蓝色点表示节点 · 🔴 红色线表示异常流量 · 🟡 黄色线表示正常流量 · 可缩放和拖动地图
          </CardDescription>
        </CardHeader>
        <CardContent>
          {isLoading && (
            <div className="flex items-center justify-center h-[600px] text-white">
              <div className="text-center">
                <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-white mx-auto mb-4"></div>
                <p>正在加载中国地图数据...</p>
              </div>
            </div>
          )}
          <div 
            ref={chartRef} 
            style={{ width: '100%', height: '600px', display: isLoading ? 'none' : 'block' }} 
          />
        </CardContent>
      </Card>

      {/* 流量详情 */}
      <Card>
        <CardHeader>
          <CardTitle>实时流量连接</CardTitle>
          <CardDescription>当前活跃的网络流量传输路径</CardDescription>
        </CardHeader>
        <CardContent>
          <div className="space-y-2 max-h-96 overflow-y-auto">
            {connections.map((conn) => (
              <div
                key={conn.id}
                className="flex items-center justify-between p-3 rounded-lg border border-slate-200 dark:border-slate-700 hover:bg-slate-50 dark:hover:bg-slate-800/50 transition-colors"
              >
                <div className="flex items-center space-x-3">
                  <div className="text-sm font-medium dark:text-white">
                    {conn.from.name}
                    <span className="mx-2 text-slate-400">→</span>
                    {conn.to.name}
                  </div>
                </div>
                <div className="flex items-center space-x-4">
                  <div className="text-sm text-slate-600 dark:text-slate-400">
                    {conn.value} GB
                  </div>
                  <div
                    className={`px-2 py-1 text-xs font-medium rounded ${
                      conn.value > 80
                        ? 'bg-red-100 text-red-800 dark:bg-red-900/20 dark:text-red-400'
                        : 'bg-green-100 text-green-800 dark:bg-green-900/20 dark:text-green-400'
                    }`}
                  >
                    {conn.value > 80 ? '⚠️ 异常' : '✓ 正常'}
                  </div>
                </div>
              </div>
            ))}
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
