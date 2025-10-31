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
    
    const ruleType = searchParams.get('ruleType');
    if (ruleType) where.ruleType = ruleType;
    
    const enabled = searchParams.get('enabled');
    if (enabled !== null) where.enabled = enabled === 'true';

    // 查询数据
    const [rules, total] = await Promise.all([
      prisma.rule.findMany({
        where,
        skip,
        take: pageSize,
        orderBy: {
          createdAt: 'desc',
        },
      }),
      prisma.rule.count({ where }),
    ]);

    return NextResponse.json({
      success: true,
      data: {
        rules,
        pagination: {
          page,
          pageSize,
          total,
          totalPages: Math.ceil(total / pageSize),
        },
      },
    });
  } catch (error) {
    console.error('Get rules error:', error);
    return NextResponse.json(
      {
        success: false,
        error: '获取规则数据失败',
      },
      { status: 500 }
    );
  }
}

export async function POST(request: NextRequest) {
  try {
    const body = await request.json();
    
    const rule = await prisma.rule.create({
      data: {
        name: body.name,
        description: body.description,
        ruleType: body.ruleType,
        pattern: body.pattern,
        action: body.action,
        severity: body.severity,
        enabled: body.enabled !== undefined ? body.enabled : true,
      },
    });

    return NextResponse.json({
      success: true,
      data: rule,
    });
  } catch (error) {
    console.error('Create rule error:', error);
    return NextResponse.json(
      {
        success: false,
        error: '创建规则失败',
      },
      { status: 500 }
    );
  }
}

