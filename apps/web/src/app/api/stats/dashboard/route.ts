import { NextRequest, NextResponse } from 'next/server';
import { prisma } from '@zhilian/database';

export async function GET(request: NextRequest) {
  try {
    // 获取总流量记录数
    const totalFlows = await prisma.flow.count();

    // 获取活跃告警数（未解决的）
    const activeAlerts = await prisma.alert.count({
      where: {
        status: {
          in: ['PENDING', 'CONFIRMED'],
        },
      },
    });

    // 获取规则数量
    const rulesCount = await prisma.rule.count({
      where: {
        enabled: true,
      },
    });

    // 获取最新的主机指标（计算健康度）
    const latestMetrics = await prisma.hostMetrics.findFirst({
      orderBy: {
        timestamp: 'desc',
      },
    });

    // 简单计算健康度（可以根据实际需求调整算法）
    const systemHealth = latestMetrics
      ? Math.min(
          100,
          100 -
            (latestMetrics.cpuUsage * 0.3 +
              latestMetrics.memoryUsage * 0.3 +
              latestMetrics.diskUsage * 0.4)
        )
      : 100;

    // 获取最近24小时的流量趋势
    const oneDayAgo = new Date(Date.now() - 24 * 60 * 60 * 1000);
    const flowTrend = await prisma.flow.groupBy({
      by: ['timestamp'],
      where: {
        timestamp: {
          gte: oneDayAgo,
        },
      },
      _count: true,
      orderBy: {
        timestamp: 'asc',
      },
    });

    // 获取攻击类型分布
    const attackTypes = await prisma.flow.groupBy({
      by: ['attackType'],
      where: {
        attackType: {
          not: null,
        },
        timestamp: {
          gte: oneDayAgo,
        },
      },
      _count: true,
    });

    // 获取最新告警
    const recentAlerts = await prisma.alert.findMany({
      take: 10,
      orderBy: {
        timestamp: 'desc',
      },
      select: {
        id: true,
        severity: true,
        alertType: true,
        title: true,
        sourceIp: true,
        timestamp: true,
        status: true,
      },
    });

    return NextResponse.json({
      success: true,
      data: {
        stats: {
          totalFlows,
          activeAlerts,
          rulesCount,
          systemHealth: Math.round(systemHealth * 10) / 10,
        },
        flowTrend,
        attackTypes,
        recentAlerts,
      },
    });
  } catch (error) {
    console.error('Dashboard stats error:', error);
    return NextResponse.json(
      {
        success: false,
        error: '获取统计数据失败',
      },
      { status: 500 }
    );
  }
}

