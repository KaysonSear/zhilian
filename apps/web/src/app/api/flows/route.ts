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
    
    const sourceIp = searchParams.get('sourceIp');
    if (sourceIp) where.sourceIp = { contains: sourceIp };
    
    const destIp = searchParams.get('destIp');
    if (destIp) where.destIp = { contains: destIp };
    
    const protocol = searchParams.get('protocol');
    if (protocol) where.protocol = protocol;
    
    const attackType = searchParams.get('attackType');
    if (attackType) where.attackType = attackType;

    const startDate = searchParams.get('startDate');
    const endDate = searchParams.get('endDate');
    if (startDate || endDate) {
      where.timestamp = {};
      if (startDate) where.timestamp.gte = new Date(startDate);
      if (endDate) where.timestamp.lte = new Date(endDate);
    }

    // 查询数据
    const [flows, total] = await Promise.all([
      prisma.flow.findMany({
        where,
        skip,
        take: pageSize,
        orderBy: {
          timestamp: 'desc',
        },
      }),
      prisma.flow.count({ where }),
    ]);

    return NextResponse.json({
      success: true,
      data: {
        flows,
        pagination: {
          page,
          pageSize,
          total,
          totalPages: Math.ceil(total / pageSize),
        },
      },
    });
  } catch (error) {
    console.error('Get flows error:', error);
    return NextResponse.json(
      {
        success: false,
        error: '获取流量数据失败',
      },
      { status: 500 }
    );
  }
}

export async function POST(request: NextRequest) {
  try {
    const body = await request.json();
    
    const flow = await prisma.flow.create({
      data: {
        sourceIp: body.sourceIp,
        destIp: body.destIp,
        sourcePort: body.sourcePort,
        destPort: body.destPort,
        protocol: body.protocol,
        packetLength: body.packetLength,
        flags: body.flags,
        attackType: body.attackType,
        confidence: body.confidence,
        isAnomalous: body.isAnomalous || false,
        geoInfo: body.geoInfo,
        metadata: body.metadata,
      },
    });

    return NextResponse.json({
      success: true,
      data: flow,
    });
  } catch (error) {
    console.error('Create flow error:', error);
    return NextResponse.json(
      {
        success: false,
        error: '创建流量记录失败',
      },
      { status: 500 }
    );
  }
}

