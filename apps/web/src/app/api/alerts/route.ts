import { NextRequest, NextResponse } from 'next/server';
import { prisma } from '@zhilian/database';

export async function GET(request: NextRequest) {
  try {
    const searchParams = request.nextUrl.searchParams;
    const page = parseInt(searchParams.get('page') || '1');
    const pageSize = parseInt(searchParams.get('pageSize') || '20');
    const skip = (page - 1) * pageSize;

    // 过滤条件
    const where: any = {};
    
    const severity = searchParams.get('severity');
    if (severity) where.severity = severity;
    
    const status = searchParams.get('status');
    if (status) where.status = status;
    
    const alertType = searchParams.get('alertType');
    if (alertType) where.alertType = { contains: alertType };

    const startDate = searchParams.get('startDate');
    const endDate = searchParams.get('endDate');
    if (startDate || endDate) {
      where.timestamp = {};
      if (startDate) where.timestamp.gte = new Date(startDate);
      if (endDate) where.timestamp.lte = new Date(endDate);
    }

    // 查询数据
    const [alerts, total] = await Promise.all([
      prisma.alert.findMany({
        where,
        skip,
        take: pageSize,
        orderBy: {
          timestamp: 'desc',
        },
      }),
      prisma.alert.count({ where }),
    ]);

    return NextResponse.json({
      success: true,
      data: {
        alerts,
        pagination: {
          page,
          pageSize,
          total,
          totalPages: Math.ceil(total / pageSize),
        },
      },
    });
  } catch (error) {
    console.error('Get alerts error:', error);
    return NextResponse.json(
      {
        success: false,
        error: '获取告警数据失败',
      },
      { status: 500 }
    );
  }
}

export async function POST(request: NextRequest) {
  try {
    const body = await request.json();
    
    const alert = await prisma.alert.create({
      data: {
        severity: body.severity,
        alertType: body.alertType,
        sourceIp: body.sourceIp,
        destIp: body.destIp,
        title: body.title,
        description: body.description,
        status: body.status || 'PENDING',
        flowId: body.flowId,
        ruleId: body.ruleId,
        metadata: body.metadata,
      },
    });

    return NextResponse.json({
      success: true,
      data: alert,
    });
  } catch (error) {
    console.error('Create alert error:', error);
    return NextResponse.json(
      {
        success: false,
        error: '创建告警失败',
      },
      { status: 500 }
    );
  }
}

