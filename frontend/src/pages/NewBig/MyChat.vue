<template>
  <div class="container" style="position:relative;">
    <div class="Box">
      <dv-border-box-2
          :color="['#4F68B7', '#4F68B7']"
          dur="10"
          style="padding: 10px; box-sizing: border-box; position: relative;"
      >
        <div style="padding: 10px; max-height: 580px; overflow-y: auto;" ref="chatContainer">
          <div class="user-chat" v-for="(chat, index) in chats" :key="index">
            <div v-if="chat.type === 'user'" class="user-input">
              <img src="@/assets/用户.png" alt="User Avatar" class="avatar"/>
              <span v-html="chat.message"></span>
            </div>
            <div v-else class="bot-response" style="position:relative;" @click="handleClick">
              <img src="@/assets/LoGO.png" alt="Bot Avatar" class="avatar" style="position: absolute;top: 10px"/>
              <img src="" alt="" class="avatar" style="visibility: hidden">
              <span v-html="chat.message"></span>
            </div>
          </div>
        </div>
        <div class="input-container">
          <div v-if="showPresetQuestions" class="preset-questions" style="position:absolute;top: -118px;width: 98.2%;">
            <ul>
              <li v-for="(question, index) in presetQuestions" :key="index" @click="selectPresetQuestion(question)">
                {{ question }}
              </li>
            </ul>
          </div>
          <input
              type="text"
              v-model="userInput"
              @keyup="checkForPresetQuestions"
              @keyup.enter="sendMessage"
              placeholder="请输入问题"
              class="input-box"
              ref="userInput"
          />
        </div>
      </dv-border-box-2>
      <div style="position:absolute;right: 150px;top:10px;width: 30px;height: 30px;z-index: 999;font-size: 20px"
           v-if="tableShow" @click="Table">&times;
      </div>
      <liuliang v-if="tableShow" style="position:absolute; top: 0; width: 75%;"/>
    </div>
  </div>
</template>

<script>
import liuliang from "@/pages/NewBig/liuliang.vue";
import html2pdf from "html2pdf.js";

export default {
  components: {
    liuliang
  },
  data() {
    return {
      tableShow: false,
      userInput: '',
      chats: [],
      showPresetQuestions: false,
      questions:[
          "将上述安全威胁报告共享到区块链平台中，并帮我查看其他共享报告"
      ],
      presetQuestions: [
        "分析此次威胁报告的信誉情报。",
        "我想要知道此处威胁的溯源链路",
        "根据分析结果生成详细的安全威胁情报报告"
      ],
      present: [
        `分析此次威胁报告的信誉情报。
          <div style="font-size: 12px;background-color:rgba(89,89,89,0.95); padding: 5px;margin-top: 5px;border-radius: 5px">
<table border="1">
  <thead>
    <tr>
      <th>序号</th>
      <th>影响资产</th>
      <th>源IP</th>
      <th>源端口</th>
      <th>攻击者IP</th>
      <th>攻击者端口</th>
      <th>协议</th>
      <th>流量大小</th>
      <th>持续时间</th>
      <th>攻击类型</th>
      <th>地理位置</th>
      <th>时间</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>1</td>
      <td>Linux虚拟机</td>
      <td>192.168.10.50</td>
      <td>80</td>
      <td>176.16.0.1</td>
      <td>1431</td>
      <td>TCP</td>
      <td>1292.55字节</td>
      <td>2.64s</td>
      <td>DDoS攻击</td>
      <td>广东深圳</td>
      <td>2024年6月13日 08:58</td>
    </tr>
  </tbody>
</table>
</div>`,
      ],
      responses: [
        "这是一个关于此次威胁报告的分析。",
      ],
      fourthReport:`
<strong>以下是关于此次安全威胁情报报告：</strong><br/>
<div style="font-size: 12px;background-color:rgba(105,105,105,0.45); padding: 5px;margin-top: 5px;border-radius: 5px">
  <h4>1. 概述</h4>
  <ul>
    <li>威胁等级：中危</li>
    <li>威胁类型：暴力破解行为（密码爆破）</li>
    <li>事件时间：2024年5月7日 5:44</li>
    <li>目标资产：
      <ul>
        <li>服务器标识: ECS-19558922</li>
        <li>私有IP地址: 192.168.0.2</li>
        <li>公有IP地址: 36.213.9.209</li>
      </ul>
    </li>
    <li>攻击IP：150.158.104.171（中国上海）</li>
    <li>攻击端口：22</li>
    <li>服务类型：SSHD（Secure Shell Daemon）</li>
    <li>攻击阶段：初始入口，横向渗透</li>
  </ul>

  <h4>2. 攻击来源</h4>
  <ul>
    <li>攻击者IP地址：150.158.104.171</li>
    <li>地理位置：上海，中国</li>
    <li>IP信誉：经常出现在恶意活动记录中，属于高风险IP，历史上涉及多次暴力破解和其他恶意活动。</li>
  </ul>

  <h4>3. 行为特征分析</h4>
  <h5>3.1 攻击工具和技术</h5>
    <div><img src="${require('@/assets/PortScan/02.png')}" alt="Image Description" style="width:600px; height: 200px;border-radius: 5px"></div>
  <ul>
    <li>使用工具：
      <ul>
        <li>Nmap或Masscan：用于端口扫描，确定目标服务器开放的22端口。</li>
        <li>Hydra或Medusa：用于自动化的SSH暴力破解，尝试大量用户名和密码组合。</li>
      </ul>
    </li>
    <li>工具配置：
      <ul>
        <li>采用字典攻击，加载一个包含常用弱密码的字典文件。</li>
        <li>利用多线程技术增加尝试速度，提高成功率。</li>
      </ul>
    </li>
    <li>攻击命令示例：
    <div style="width: 550px;box-sizing : border-box">
          <div style="border-radius: 5px 5px 0 0;background-color:#2F2F2F;padding: 5px 10px;font-size: 12px">bash</div>
          <div style="border-radius: 0 0 5px 5px ;background-color:#0D0D0D;color: #f3f3f3;padding: 15px 10px;font-size: 14px">hydra -l root -P /path/to/password_list.txt ssh://36.213.9.209 -t 4</div>
        </div>
    </li>
  </ul>

  <h5>3.2 攻击步骤</h5>
  <h5>攻击溯源链路:</h5>
  <div><img src="${require('@/assets/PortScan/01.png')}" alt="Image Description" style="width:600px; height: 100px;border-radius: 5px"></div>
  <ul>
    <li>扫描阶段：
      <ul>
        <li>利用Masscan快速扫描，发现目标IP的22端口开放，准备进行暴力破解。</li>
      </ul>
    </li>
    <li>暴力破解：
      <ul>
        <li>使用Hydra工具，对目标SSH服务进行密码爆破。通过高频率的登录尝试，寻求突破口。</li>
      </ul>
    </li>
    <li>成功登录：
      <ul>
        <li>攻击者找到有效凭证后成功登录，获取SSH访问权限。可能利用成功的登录尝试记录下登录时间和成功使用的密码。</li>
      </ul>
    </li>
    <li>横向渗透：
      <ul>
        <li>一旦进入服务器，攻击者在内网中进行横向扫描。使用Nmap识别其他设备和服务的可用性，寻找进一步渗透的机会。</li>
        <li>利用已知漏洞工具（如Metasploit）进行进一步攻击。</li>
      </ul>
    </li>
  </ul>

  <h4>4. 详细攻击过程</h4>
  <h5>4.1 初始访问</h5>
  <ul>
    <li>攻击者通过暴力破解获得初始访问，成功使用SSH协议连接到服务器。</li>
    <li>探索服务器环境，识别关键数据和服务。</li>
    <li>查找可能的文件和配置，以获取更多信息和进一步利用。</li>
  </ul>

  <h5>4.2 内部网络扫描</h5>
  <ul>
    <li>扫描命令：
      <div style="width: 550px;box-sizing : border-box">
          <div style="border-radius: 5px 5px 0 0;background-color:#2F2F2F;padding: 5px 10px;font-size: 12px">bash</div>
          <div style="border-radius: 0 0 5px 5px ;background-color:#0D0D0D;color: #f3f3f3;padding: 15px 10px;font-size: 14px">nmap -sP 192.168.0.0/24</div>
        </div>
    </li>
    <li>目标：寻找同一网段内的其他在线设备。</li>
    <li>目的：识别内部网络结构和潜在的薄弱点，以便进行进一步渗透。</li>
  </ul>

  <h5>4.3 漏洞利用</h5>
  <ul>
    <li>攻击者可能上传或执行恶意脚本，进行持久化攻击。</li>
    <li>使用Metasploit等工具利用内部系统的已知漏洞，扩大攻击面。</li>
  </ul>

  <h4>5. 防御措施建议</h4>
  <div><img src="${require('@/assets/PortScan/03.png')}" alt="Image Description" style="width:300px; height: 500px;border-radius: 5px"></div>
  <h5>5.1 加强身份验证</h5>
  <ul>
    <li>启用双因素认证：对SSH服务启用双因素认证，增加安全层。</li>
    <li>强密码策略：强制使用复杂密码，并定期更换。</li>
  </ul>

  <h5>5.2 监控和检测</h5>
  <ul>
    <li>实时监控：部署入侵检测系统（IDS），实时监控和分析SSH登录尝试。</li>
    <li>日志分析：定期分析登录日志，识别并报警可疑活动。</li>
  </ul>

  <h5>5.3 网络安全策略</h5>
  <ul>
    <li>限制SSH访问：仅允许可信任的IP地址访问SSH服务，使用防火墙策略。</li>
    <li>内网隔离：对重要资产进行网络隔离，防止横向渗透。</li>
  </ul>

  <h5>5.4 安全培训和应急响应</h5>
  <ul>
    <li>安全意识培训：定期培训员工，提升安全意识，防止社会工程学攻击。</li>
    <li>应急响应计划：制定并演练安全事件应急响应计划，确保在攻击发生时能够快速响应和恢复。</li>
  </ul>
  <h4>6. 结论</h4>
  <p>本次安全事件展示了攻击者如何通过暴力破解手段获得未授权访问并尝试进行横向渗透。为了防止类似攻击，建议实施多层次的安全措施，增强系统的防御能力，确保关键资产的安全。通过以上措施，可以显著降低系统被暴力破解攻击的风险，并在事件发生时快速响应和缓解影响。</p>
</div>
 <div style="color: #7995f6; font-size: 12px; margin-top: 15px; text-decoration: underline;" id="click-download">下载导出</div>
`,
      firstReport: `<strong>以下是此次DDoS攻击的安全威胁分析报告：</strong><br/>
    <div style="font-size: 12px;background-color:rgba(105,105,105,0.45); padding: 10px;margin-top: 10px;border-radius: 5px">
      <h4>1. 概述</h4>
      <ul>
        <li><strong>风险等级：</strong> 高风险</li>
        <li><strong>攻击类型：</strong> DDoS攻击</li>
        <li><strong>影响资产：</strong>
          <ul>
            <li>服务器标识: Linux虚拟机</li>
            <li>私有IP地址:192.168.10.50</li>
            <li>公有IP地址: 36.213.9.209</li>
          </ul>
        </li>
      </ul>
       <h4>2. 信誉情报分析</h4>
      <h5>2.1 攻击来源分析</h5>
      <ul>
        <li><strong>攻击IP地址：</strong> 172.16.0.1 (广东深圳)</li>
             <li><strong>最近攻击时间：</strong> 2024年6月13日 08:58</li>

         <li>攻击端口: 14301</li>
            <li>服务类型: TCP</li>
            <li>平均包长: 1163.3字节</li>
        <li><strong>IP信誉：</strong>
          <ul>
            <li>此IP地址在多个安全情报数据库中被标记为参与DDoS攻击的来源，历史上多次出现在恶意活动记录中。</li>
            <li>属于高风险IP，经常与多个僵尸网络和DDoS攻击工具相关联。</li>
          </ul>
        </li>
        <li><strong>风险评估：</strong>
          <ul>
            <li>高风险：此IP地址的历史活动表明其可能被用于协调多个僵尸节点发起DDoS攻击，尤其是针对TCP服务的攻击。</li>
          </ul>
        </li>
      </ul>
      <h5>2.2 攻击特征分析</h5>
      <ul>
        <li><strong>攻击类型：</strong> DDoS（分布式拒绝服务攻击）</li>
        <li><strong>描述：</strong>
          <ul>
            <li>此类攻击通过向目标服务器发送大量数据包，耗尽其资源，使其无法处理合法请求。</li>
            <li>典型表现为目标服务器响应时间明显增加，甚至完全无法响应。</li>
          </ul>
        </li>
        <li><strong>攻击特征：</strong>
          <ul>
            <li>流量异常：短时间内流量异常激增。</li>
            <li>包长特征：平均包长为1163.3字节，表明可能使用了伪造或特定模式的数据包。</li>
            <li>多点攻击：攻击可能来自多个IP（虽然此次仅分析到一个来源IP，但通常DDoS会结合多IP进行）。</li>
          </ul>
        </li>
      </ul>
    </div>`,
      secoondReport: ` <strong>溯源链路是对攻击过程的深入解析，以便准确确定攻击路径和手段。以下是详细的攻击溯源分析：</strong><br/>
<div style="font-size: 12px; background-color:rgba(105,105,105,0.45); padding: 10px; margin-top: 10px; border-radius: 5px">
      <h4>1. 溯源链路分析</h4>
      <h5>1.1 攻击路径</h5>
      <ul>
        <li><strong>攻击发起节点：</strong>
          <ul>
            <li>IP地址: 172.16.0.1 (广东深圳)</li>
            <li>可能的角色: 僵尸网络的一个节点，可能通过感染恶意软件加入攻击者的网络。</li>
          </ul>
        </li>
        <li><strong>僵尸网络控制器（C&C服务器）：</strong>
          <ul>
            <li>描述: 攻击者通常通过C&C（Command and Control）服务器指挥多个僵尸节点进行DDoS攻击。</li>
            <li>可能特征: 使用加密通信和变化IP地址以隐藏其真实位置。</li>
          </ul>
        </li>
        <li><strong>流量方向：</strong>
          <ul>
            <li>从多个僵尸节点发起大量TCP数据包，目标是耗尽受害者服务器的资源。</li>
            <li>中间节点: 路由器、交换机等中间网络设备可能无法阻止或检测这种攻击，因为流量看似合法。</li>
          </ul>
        </li>
        <li><strong>目标服务器：</strong>
          <ul>
            <li>目标IP: 192.168.10.50</li>
            <li>攻击效果: 服务被大量的伪造流量淹没，导致正常用户的请求无法得到响应。</li>
          </ul>
        </li>
      </ul>

      <h5>1.2 攻击工具及方法</h5>
      <ul>
        <li><strong>工具：</strong> 常用的DDoS攻击工具可能包括LOIC（Low Orbit Ion Cannon）、HOIC（High Orbit Ion Cannon）等，结合自动化脚本以增加攻击强度。</li>
        <li><strong>攻击方法：</strong>
          <ul>
            <li>反射攻击: 利用开放的服务（如DNS、NTP）进行放大攻击。</li>
            <li>洪水攻击: 大量伪造请求以TCP SYN洪水、UDP洪水等方式对目标服务器施压。</li>
          </ul>
        </li>
      </ul>
       <p>·溯源链路图</p>
       <div><img src="${require('@/assets/MyData/01.png')}" alt="Image Description" style="width:600px; height: 200px;border-radius: 5px"></div>
      </div>
`,
      threeReport: `
<strong>以下是关于此次安全威胁情报报告：</strong><br/>
    <div style="font-size: 12px; background-color:rgba(105,105,105,0.45); padding: 10px; margin-top: 10px; border-radius: 5px">
      <h4>安全威胁情报报告</h4>

      <h4>1. 概述</h4>
      <ul>
        <li><strong>风险等级：</strong> 高风险</li>
        <li><strong>攻击类型：</strong> DDoS攻击</li>
        <li><strong>影响资产：</strong>
          <ul>
            <li>服务器标识: Linux虚拟机</li>
            <li>私有IP地址: 192.168.10.50</li>
            <li>公有IP地址: 36.213.9.209</li>
          </ul>
        </li>
        <li><strong>最近攻击时间：</strong> 2024年6月13日 08:58</li>
      </ul>

      <h4>2. 信誉情报分析</h4>
      <h4>2.1 攻击来源分析</h4>
      <ul>
        <li><strong>攻击IP地址：</strong> 172.16.0.1 (广东深圳)</li>
        <li><strong>IP信誉：</strong>
          <ul>
            <li>此IP地址在多个安全情报数据库中被标记为参与DDoS攻击的来源，历史上多次出现在恶意活动记录中。</li>
            <li>属于高风险IP，经常与多个僵尸网络和DDoS攻击工具相关联。</li>
          </ul>
        </li>
        <li><strong>风险评估：</strong>
          <ul>
            <li>高风险：此IP地址的历史活动表明其可能被用于协调多个僵尸节点发起DDoS攻击，尤其是针对TCP服务的攻击。</li>
          </ul>
        </li>
      </ul>

      <h4>2.2 攻击特征分析</h4>
      <ul>
        <li><strong>攻击类型：</strong> DDoS（分布式拒绝服务攻击）</li>
        <li><strong>描述：</strong>
          <ul>
            <li>此类攻击通过向目标服务器发送大量数据包，耗尽其资源，使其无法处理合法请求。</li>
            <li>典型表现为目标服务器响应时间明显增加，甚至完全无法响应。</li>
          </ul>
        </li>
        <li><strong>攻击特征：</strong>
          <ul>
            <li>流量异常：短时间内流量异常激增。</li>
            <li>包长特征：平均包长为1163.3字节，表明可能使用了伪造或特定模式的数据包。</li>
            <li>多点攻击：攻击可能来自多个IP（虽然此次仅分析到一个来源IP，但通常DDoS会结合多IP进行）。</li>
          </ul>
        </li>
      </ul>

      <h4>2.3 溯源链路分析</h4>
      <div><img src="${require('@/assets/MyData/01.png')}" alt="Image Description" style="width:600px; height: 200px;border-radius: 5px"></div>
      <ul>
        <li><strong>攻击路径：</strong>
          <ul>
            <li>攻击发起节点: 172.16.0.1 (广东深圳)</li>
            <li>僵尸网络控制器（C&C服务器）:
              <ul>
                <li>描述: 攻击者通过C&C服务器指挥多个僵尸节点进行DDoS攻击。</li>
                <li>可能特征: 使用加密通信和变化IP地址以隐藏其真实位置。</li>
              </ul>
            </li>
            <li>流量方向:
              <ul>
                <li>从多个僵尸节点发起大量TCP数据包，目标是耗尽受害者服务器的资源。</li>
                <li>中间节点: 路由器、交换机等中间网络设备可能无法阻止或检测这种攻击，因为流量看似合法。</li>
              </ul>
            </li>
            <li>目标服务器:
              <ul>
                <li>目标IP: 36.213.9.209</li>
                <li>攻击效果: 服务被大量的伪造流量淹没，导致正常用户的请求无法得到响应。</li>
              </ul>
            </li>
          </ul>
        </li>
      </ul>

      <h4>2.4 攻击工具及方法</h4>
      <div><img src="${require('@/assets/MyData/02.png')}" alt="Image Description" style="width:450px; height: 300px;border-radius: 5px"></div>
      <ul>
        <li><strong>工具：</strong> 常用的DDoS攻击工具包括LOIC（Low Orbit Ion Cannon）、HOIC（High Orbit Ion Cannon）等，结合自动化脚本以增加攻击强度。</li>
        <li><strong>攻击方法：</strong>
          <ul>
            <li>反射攻击: 利用开放的服务（如DNS、NTP）进行放大攻击。</li>
            <li>洪水攻击: 大量伪造请求以TCP SYN洪水、UDP洪水等方式对目标服务器施压。</li>
          </ul>
        </li>
        <li><strong>攻击命令：</strong>
          <ul>
            <li>本次DDoS攻击可能使用了以下假设命令：</li>
            <div style="width: 750px;box-sizing : border-box">
          <div style="border-radius: 5px 5px 0 0;background-color:#2F2F2F;padding: 5px 10px;font-size: 12px">bash</div>
          <div style="border-radius: 0 0 5px 5px ;background-color:#0D0D0D;color: #f3f3f3;padding: 15px 10px;font-size: 14px">./attack_tool --target 36.213.9.209 --method tcp --flood --threads 1000 --duration 3600</div>
        </div>
            <ul>
              <li>攻击工具: 自定义或下载的攻击工具。</li>
              <li>目标: IP为36.213.9.209的服务器。</li>
              <li>攻击方式: TCP洪水。</li>
              <li>线程: 使用1000个线程。</li>
              <li>持续时间: 攻击持续3600秒（1小时）。</li>
            </ul>
          </ul>
        </li>
      </ul>

      <h4>3. 防御建议</h4>
      <div><img src="${require('@/assets/MyData/03.png')}" alt="Image Description" style="width:450px; height: 450px;border-radius: 5px"></div>
      <ul>
        <li><strong>实时监控：</strong>
          <ul>
            <li>流量监控: 实时监控网络流量，识别异常高流量的来源。</li>
            <li>行为分析: 使用行为分析工具检测潜在的攻击行为。</li>
          </ul>
        </li>
        <li><strong>防火墙规则：</strong>
          <ul>
            <li>访问控制: 限制对重要服务的访问，仅允许可信来源IP。</li>
            <li>速率限制: 配置速率限制，减少每个IP的连接速率，降低攻击影响。</li>
          </ul>
        </li>
        <li><strong>使用DDoS防护服务：</strong>
          <ul>
            <li>云防护: 使用云端DDoS防护服务，进行流量清洗。</li>
            <li>边界防护设备: 部署硬件防火墙，自动过滤异常流量。</li>
          </ul>
        </li>
        <li><strong>安全策略更新：</strong>
          <ul>
            <li>补丁管理: 定期更新系统和应用补丁，减少被利用的漏洞。</li>
            <li>应急响应: 制定并演练应急响应计划，确保在攻击发生时能够快速反应。</li>
          </ul>
        </li>
      </ul>
      <h4>6. 结论</h4>
  <p>本次事件中，我们分析了一起高风险的DDoS攻击。攻击目标服务器（Linux虚拟机）因遭到来自僵尸网络的大量恶意流量而资源耗尽，导致服务中断。攻击由广东深圳的攻击者IP（172.16.0.1）发起，通过多个僵尸节点协调执行，显示出组织化和隐蔽性。

攻击者利用C&C服务器指挥僵尸节点，采用TCP洪水、反射放大等多种方式对目标服务器进行压制，旨在耗尽网络带宽和计算资源。这种攻击严重影响正常用户访问，可能导致业务中断和经济损失。</p></div>
<div style="color: #7995f6; font-size: 12px; margin-top: 15px; text-decoration: underline;" id="click-download">下载导出</div>
`,
      shareRespond: `
<div>
         <div>ID:af515485215454,区块链账号:admin</div>
        <p>1. 已验证身份。成功将您的安全威胁报告c12659962678共享到区块链平台！</p>
        <h4>2. 现在我将检索区块链共享平台上的安全威胁情报。请稍候...</h4>
        <div style="font-size: 14px; background-color: rgba(230, 230, 250, 0.2); padding: 15px; margin-top: 15px; border-radius: 8px;">
            <p>以下是从区块链平台获取的其他共享安全威胁报告：</p>
            <ul style="margin-top: 10px;">
                <li style="margin-bottom: 5px; color: #7995f6; width: 450px; display: flex; justify-content: space-between;">
                    <div><strong>1、ID:</strong> a6598255678,</div>
                    <div><strong>标题:</strong> 恶意软件分析,</div>
                    <div><strong>日期:</strong> 2024年6月12日</div>
                </li>
                <li id="click-file" style="margin-bottom: 5px; color: #7995f6; width: 450px; display: flex; justify-content: space-between;">
                    <div><strong>2、ID:</strong> f52652659432,</div>
                    <div><strong>标题:</strong> 暴力破解登陆,</div>
                    <div><strong>日期:</strong> 2024年5月28日</div>
                </li>
                <li style="margin-bottom: 5px; color: #7995f6; width: 450px; display: flex; justify-content: space-between;">
                    <div><strong>3、ID:</strong> c12659962678,</div>
                    <div><strong>标题:</strong> DDoS攻击,</div>
                    <div><strong>日期:</strong> 2024年6月13日</div>
                </li>
                <li style="margin-bottom: 5px; color: #7995f6; width: 450px; display: flex; justify-content: space-between;">
                    <div><strong>4、ID:</strong> a65498595478,</div>
                    <div><strong>标题:</strong> 未授权访问警报,</div>
                    <div><strong>日期:</strong> 2024年6月13日</div>
                </li>
            </ul>
        </div>
    </div>
`
    };
  },
  mounted() {
    // 页面加载后自动发送第一个预设问题
    this.userInput = ' .';
    setTimeout(() => {
      this.sendMessage();
    }, 1000)
  },
  methods: {
    handleClick(event) {
      if (event.target && event.target.id === 'click-more') {
        this.Table(); // 调用 Vue 实例中的方法
      } else if (event.target && event.target.id === 'click-download') {
        const element = this.threeReport // 获取需要转换为PDF的DOM元素
        this.$message.success('下载成功！');
        html2pdf().from(element).save('security_report.pdf'); // 将DOM元素转换为PDF并保存
      } else if (event.target && event.target.id === 'click-file') {
        const element = this.fourthReport // 获取需要转换为PDF的DOM元素
        this.$message.success('下载成功！');
        html2pdf().from(element).save('security_report.pdf'); // 将DOM元素转换为PDF并保存
      }
    },
    sendMessage() {
      if (this.userInput.trim() !== '') {
        let userMessage = ''
        if (this.userInput === ' .') {
          userMessage = this.present[0];
        } else {
          userMessage = this.userInput
        }
        this.chats.push({type: 'user', message: userMessage});
        this.userInput = '';
        this.scrollToBottom(); // 在用户发送消息后滚动到底部

        if (userMessage === this.present[0]) {
          setTimeout(() => {
            this.showIntelligenceReport(0);
          }, 1000);
        } else if (userMessage === this.presetQuestions[1]) {
          this.showIntelligenceReport(1);
        } else if (userMessage === this.presetQuestions[2]) {
          this.showIntelligenceReport(2);
        } else if (userMessage === this.questions[0]) {
          this.showIntelligenceReport(3);
        }
        else {
          setTimeout(this.botResponse, 1000);
        }
      }
    },


    Table() {
      this.tableShow = !this.tableShow;
      console.log(66666);
    },
    checkForPresetQuestions(event) {
      if (event.key === "\\") {
        this.showPresetQuestions = true;
      } else {
        this.showPresetQuestions = false;
      }
    },
    selectPresetQuestion(question) {
      this.userInput = question;
      this.showPresetQuestions = false;
      this.$nextTick(() => {
        this.$refs.userInput.focus();  // 聚焦输入框
      });
    },
    botResponse() {
      const randomIndex = Math.floor(Math.random() * this.responses.length);
      const response = this.responses[randomIndex];
      this.chats.push({type: 'bot', message: ''}); // 先插入一个空消息
      this.scrollToBottom(); // 在机器人响应后滚动到底部
      this.typeOutMessage(response, this.chats.length - 1);
    },
    showIntelligenceReport(index) {
      this.scrollToBottom();
      this.displayFullIntelligenceReport(index);
    },
    displayFullIntelligenceReport(index) {
      let report = ''
      if (index === 0) {
        report = this.firstReport;
      } else if (index === 1) {
        report = this.secoondReport;
      } else if (index === 2) {
        report = this.threeReport;
      }else if (index === 3) {
        report = this.shareRespond;
      }
      this.chats.push({type: 'bot', message: ''}); // 插入一条空消息用于报告
      this.typeOutMessage(report, this.chats.length - 1);
    },
    typeOutMessage(message, chatIndex, callback = null) {
      let i = 0;
      const interval = setInterval(() => {
        if (i < message.length) {
          this.chats[chatIndex].message += message[i];
          i++;
          this.scrollToBottom(); // 每次增加一个字母后滚动到底部
        } else {
          clearInterval(interval);
          if (callback) callback();
        }
      }, 5); // 每50ms显示一个字符，调节打字速度
    },
    scrollToBottom() {
      this.$nextTick(() => {
        const container = this.$refs.chatContainer; // 使用 ref 来选择容器
        container.scrollTop = container.scrollHeight; // 滚动到容器的底部
      });
    }
  }
};
</script>

<style scoped>
.container {
  padding: 0 30px;
  height: 100vh;
  position: relative;
}

.Box {
  width: 85%;
  height: 80%;
  margin: 2% auto;
  background-color: rgba(59, 59, 59, 0.2);
}

.user-chat {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
  transition: width 0.3s ease; /* 添加宽度变化的过渡效果 */
}

.user-input,
.bot-response {
  display: flex;
  align-items: center;
  background-color: rgba(0, 0, 0, 0.2);
  color: #fff;
  padding: 10px;
  border-radius: 5px;
  margin-bottom: 5px;
  transition: width 0.3s ease; /* 添加宽度变化的过渡效果 */
}

.user-input {
  background-color: rgba(31, 164, 252, 0.2);
  color: #ffffff;
}

.bot-response {
  background-color: rgba(60, 60, 60, 0.8); /* 设置为稍微深色背景 */
  color: #fff;
  font-size: 0.9rem; /* 设置较小的字体 */
  line-height: 1.5;
}

.bot-response strong {
  font-size: 1rem;
  color: #fff;
}

.bot-response h4,
.bot-response h5 {
  margin: 5px 0;
  color: #f39c12;
  font-weight: bold;
}

.bot-response ul {
  padding-left: 20px;
}

.bot-response li {
  margin-bottom: 5px;
}

.avatar {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  margin-right: 10px;
}

.input-container {
  position: absolute;
  bottom: 10px;
  width: 100%;
  padding: 10px;
  box-sizing: border-box;
}

.input-box {
  width: 100%;
  padding: 10px;
  border: 1px solid #4F68B7;
  border-radius: 5px;
  box-sizing: border-box;
  background-color: rgba(50, 50, 50, 0.8); /* 暗色背景 */
  color: #fff; /* 白色文字 */
}

.preset-questions {
  background-color: rgba(30, 30, 30, 0.9); /* 更深的暗色背景 */
  border: 1px solid #333;
  border-radius: 5px;
  margin-bottom: 10px;
  max-height: 150px;
  overflow-y: auto;
  position: absolute;
  width: 100%;
  box-sizing: border-box;
  color: #fff;
}

.preset-questions ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.preset-questions li {
  padding: 10px;
  cursor: pointer;
  border-bottom: 1px solid #444;
}

.preset-questions li:hover {
  background-color: #555;
}
</style>
