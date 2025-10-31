import { NextRequest, NextResponse } from 'next/server';
import { prisma } from '@zhilian/database';

export async function GET(
  request: NextRequest,
  { params }: { params: Promise<{ id: string }> }
) {
  try {
    const { id } = await params;
    const rule = await prisma.rule.findUnique({
      where: { id },
    });

    if (!rule) {
      return NextResponse.json(
        {
          success: false,
          error: '规则不存在',
        },
        { status: 404 }
      );
    }

    return NextResponse.json({
      success: true,
      data: rule,
    });
  } catch (error) {
    console.error('Get rule error:', error);
    return NextResponse.json(
      {
        success: false,
        error: '获取规则详情失败',
      },
      { status: 500 }
    );
  }
}

export async function PATCH(
  request: NextRequest,
  { params }: { params: Promise<{ id: string }> }
) {
  try {
    const { id } = await params;
    const body = await request.json();

    const rule = await prisma.rule.update({
      where: { id },
      data: {
        ...(body.name && { name: body.name }),
        ...(body.description !== undefined && { description: body.description }),
        ...(body.ruleType && { ruleType: body.ruleType }),
        ...(body.pattern && { pattern: body.pattern }),
        ...(body.action && { action: body.action }),
        ...(body.severity && { severity: body.severity }),
        ...(body.enabled !== undefined && { enabled: body.enabled }),
      },
    });

    return NextResponse.json({
      success: true,
      data: rule,
    });
  } catch (error) {
    console.error('Update rule error:', error);
    return NextResponse.json(
      {
        success: false,
        error: '更新规则失败',
      },
      { status: 500 }
    );
  }
}

export async function DELETE(
  request: NextRequest,
  { params }: { params: Promise<{ id: string }> }
) {
  try {
    const { id } = await params;
    await prisma.rule.delete({
      where: { id },
    });

    return NextResponse.json({
      success: true,
      message: '规则删除成功',
    });
  } catch (error) {
    console.error('Delete rule error:', error);
    return NextResponse.json(
      {
        success: false,
        error: '删除规则失败',
      },
      { status: 500 }
    );
  }
}

