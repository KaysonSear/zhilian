import { NextRequest, NextResponse } from 'next/server';
import { prisma } from '@zhilian/database';

export async function GET(request: NextRequest) {
  try {
    const searchParams = request.nextUrl.searchParams;
    const hostname = searchParams.get('hostname');
    const limit = parseInt(searchParams.get('limit') || '100');
    
    // 时间范围
    const startDate = searchParams.get('startDate');
    const endDate = searchParams.get('endDate');
    
    const where: any = {};
    if (hostname) where.hostname = hostname;
    if (startDate || endDate) {
      where.timestamp = {};
      if (startDate) where.timestamp.gte = new Date(startDate);
      if (endDate) where.timestamp.lte = new Date(endDate);
    }

    const metrics = await prisma.hostMetrics.findMany({
      where,
      take: limit,
      orderBy: {
        timestamp: 'desc',
      },
    });

    return NextResponse.json({
      success: true,
      data: metrics,
    });
  } catch (error) {
    console.error('Get host metrics error:', error);
    return NextResponse.json(
      {
        success: false,
        error: '获取主机指标失败',
      },
      { status: 500 }
    );
  }
}

export async function POST(request: NextRequest) {
  try {
    const body = await request.json();
    
    const metrics = await prisma.hostMetrics.create({
      data: {
        hostname: body.hostname,
        cpuUsage: body.cpuUsage,
        memoryUsage: body.memoryUsage,
        diskUsage: body.diskUsage,
        networkIn: body.networkIn,
        networkOut: body.networkOut,
        processes: body.processes,
        metadata: body.metadata,
      },
    });

    return NextResponse.json({
      success: true,
      data: metrics,
    });
  } catch (error) {
    console.error('Create host metrics error:', error);
    return NextResponse.json(
      {
        success: false,
        error: '创建主机指标失败',
      },
      { status: 500 }
    );
  }
}

