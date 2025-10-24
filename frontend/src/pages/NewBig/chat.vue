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
              <img src="@/assets/用户.png" alt="User Avatar" class="avatar" />
              <span>{{ chat.message }}</span>
            </div>
            <div v-else class="bot-response" style="position:relative;" @click="handleClick">
              <img src="@/assets/LoGO.png" alt="Bot Avatar" class="avatar"  style="position: absolute;top: 10px"/>
              <img src="" alt="" class="avatar" style="visibility: hidden">
              <span v-html="chat.message"></span>
            </div>
          </div>
        </div>
        <div class="input-container">
          <div v-if="showPresetQuestions" class="preset-questions" style="position:absolute;top: -150px;width: 98.2%;">
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
      <div style="position:absolute;right: 150px;top:10px;width: 30px;height: 30px;z-index: 999;font-size: 20px" v-if="tableShow" @click="Table">&times;</div>
      <liuliang  v-if="tableShow" style="position:absolute; top: 0; width: 75%;"/>
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
      tableShow:false,
      userInput: '',
      chats: [],
      showPresetQuestions: false,
      presetQuestions: [
        "分析此次威胁报告的信誉情报。",
        "我想要知道此处威胁的溯源链路",
        "根据分析结果生成详细的安全威胁情报报告",
        "我想要查看最近一天的网络流量。",
        "详细分析一下这段时间内出现的威胁攻击"
      ],
      responses: [
        "你好，我是机器人。",
        "你有什么问题？",
        "我可以帮助你分析网络安全问题。",
        "谢谢你的提问。",
        "再见，有需要随时联系我。"
      ],
      intelligenceReport: `
        <strong>以下是关于此次威胁报告的信誉情报分析：</strong><br/>
           <div style="font-size: 12px;background-color:rgba(105,105,105,0.45); padding: 5px;margin-top: 5px;border-radius: 5px">
        <h4>1. 威胁事件概要</h4>
        <ul>
          <li>威胁等级：中危</li>
          <li>攻击类型：暴力破解行为，密码爆破</li>
          <li>目标资产：
            <ul>
              <li>ECS-19558922</li>
              <li>私有IP地址: 192.168.0.2</li>
              <li>公有IP地址: 36.213.9.209</li>
            </ul>
          </li>
          <li>攻击细节：
            <ul>
              <li>攻击IP: 150.158.104.171（中国上海）</li>
              <li>攻击端口: 22</li>
              <li>服务类型: SSHD (Secure Shell Daemon)</li>
              <li>攻击阶段: 初始入口，横向渗透</li>
              <li>攻击时间: 2024年5月7日 5:44</li>
            </ul>
          </li>
        </ul>
        <h4>2. 威胁情报分析</h4>
        <h5>2.1 攻击IP情报分析</h5>
        <ul>
          <li>IP地址: 150.158.104.171
            <ul>
              <li>地理位置: 中国上海</li>
              <li>历史记录: 此IP地址在安全社区和情报数据库中曾多次报告与暴力破解和其他恶意活动相关。</li>
              <li>风险评估: 高风险。过去与多种网络攻击类型相关，通常出现在攻击者使用的僵尸网络和指挥控制服务器中。</li>
            </ul>
          </li>
        </ul>
        <h5>2.2 攻击行为特征</h5>
        <ul>
          <li>暴力破解行为：
            <ul>
              <li>使用自动化工具对SSH服务进行密码尝试，目标是使用弱口令或默认密码进行未授权访问。</li>
              <li>常用工具包括Hydra、Medusa等，这些工具可以快速尝试多种用户名和密码组合。</li>
            </ul>
          </li>
          <li>横向渗透：
            <ul>
              <li>一旦获得初始访问，攻击者可能会利用该访问权限在网络中移动，寻找多系统的访问权限。</li>
              <li>这种行为通常伴随着网络扫描并利用其漏洞扩展攻击面。</li>
            </ul>
          </li>
        </ul>
        </div>
      `,
      secondReport:`
       <strong>溯源链路是对攻击过程的深入解析，以便准确确定攻击路径和手段。以下是详细的攻击溯源分析：</strong><br/>
           <div style="font-size: 12px;background-color:rgba(105,105,105,0.45); padding: 5px;margin-top: 5px;border-radius: 5px">
              <h4>一. 攻击起始</h4>
              <ul>
                <li>1.扫描阶段：攻击者使用工具<span style="color: #98adfa">Nmap</span>对互联网进行端口扫描，以发现暴露的SSH服务。<br>确定目标服务器36.213.9.209的22端口处于开放状态，并可进行连接。</li>
                <li>2.攻击阶段：
                  <ul>
                    <li>工具配置：攻击者配置暴力破解工具(Hydra)，指定目标IP和端口，并载入字典文件。</li>
                    <li>并发尝试：工具对SSH服务进行并发的密码尝试，使用多个线程以加快破解速度。</li>
                  </ul>
                </li>
              </ul>
              <h4>二. 具体过程:</h4>
              <ul>
               <li>扫描命令：
                  <ul>
                    <li>攻击者利用获取的SSH访问权限登录服务器。</li>
                    <li>使用Nmap对内部网络进行扫描，以识别其他可访问和脆弱的系统。</li>
                  </ul>
                    <div style="width: 550px;box-sizing : border-box">
          <div style="border-radius: 5px 5px 0 0;background-color:#2F2F2F;padding: 5px 10px;font-size: 12px">bash</div>
          <div style="border-radius: 0 0 5px 5px ;background-color:#0D0D0D;color: #f3f3f3;padding: 15px 10px;font-size: 14px">nmap -sP 192.168.0.0/24</div>
        </div>
                <li>攻击命令：
                <ul>
                    <li>-l root: 指定用户名（root）</li>
                    <li>-P D:/huang/file/password_list.txt: 指定密码字典文件。</li>
                    <li>-t 4: 使用4个线程同时进行尝试。</li>
                  </ul>
                    <div style="width: 550px;box-sizing : border-box">
          <div style="border-radius: 5px 5px 0 0;background-color:#2F2F2F;padding: 5px 10px;font-size: 12px">bash</div>
          <div style="border-radius: 0 0 5px 5px ;background-color:#0D0D0D;color: #f3f3f3;padding: 15px 10px;font-size: 14px">hydra -l root -P D:/huang/file/password_list.txt ssh://36.213.9.209 -t 4</div>

        </div>

                </li>

              </ul>
                <h4>三. 溯源链路图示:</h4>
                 <ul>

                <div><img src="${require('@/assets/暴力破解.jpg')}" alt="Image Description" style="width:600px; height: 100px;"></div>
              </ul>
            </div>
      `,
      threeReport:`
       <strong>我将为您展示出服务器：Linux虚拟机最近一天的流量情况</strong><br/>
       <div style="font-size: 12px;background-color:rgba(105,105,105,0.45); padding: 5px;margin-top: 5px;border-radius: 5px">
<table border="1">
  <thead>
    <tr>
      <th>序号</th>
      <th>时间</th>
      <th>源IP</th>
      <th>目标IP地址</th>
      <th>源端口</th>
      <th>目标端口</th>
      <th>协议</th>
      <th>流量大小</th>
      <th>流量包数</th>
      <th>流量类型</th>
      <th>持续时间</th>
      <th>地理位置</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>1</td>
      <td>2024-06-13 00:01</td>
      <td>192.168.0.10</td>
      <td>203.0.113.11</td>
      <td>8080</td>
      <td>22</td>
      <td>TCP</td>
      <td>500 KB</td>
      <td>20</td>
      <td>HTTP</td>
      <td>30s</td>
      <td>上海, 中国</td>
    </tr>
  </tbody>
</table>
</div>
 <div style="color: #7995f6; font-size: 12px; margin-top: 15px; text-decoration: underline;" id="click-more">点击查看更多</div>
      `,
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
      // 自收集数据分析威胁数据来源
      MyDataReport:`<strong>以下是TransEC-GAN模型分析预测出的网络威胁攻击：</strong><br/>
    <div style="font-size: 12px;background-color:rgba(105,105,105,0.45); padding: 5px;margin-top: 5px;border-radius: 5px">
      <h4>1. 概述</h4>
      <ul>
        <li>总流量数据分析时间段：2024年6月13日 00:00 - 2024年6月13日 23:59</li>
        <li>识别出的攻击类型：
          <ul>
            <li>DDoS：32条</li>
            <li>PortScan：86条</li>
            <li>DoS_Hulk：72条</li>
            <li>DoS_GoldenEye：2条</li>
          </ul>
        </li>
      </ul>

    <h4>2. 攻击类型分析</h4>
      <h5>2.1 DDoS 攻击</h5>
      <ul>
        <li><strong>攻击描述：</strong>DDoS攻击通过大量的请求使目标服务器无法响应正常用户的请求。这类攻击通常来自分布式的IP地址。</li>
        <li><strong>特征分析：</strong>
          <ul>
            <li>流量高峰：在多个时间段内发现流量高峰。</li>
            <li>IP分布：来源IP非常分散，显示攻击来自多个节点。</li>
          </ul>
        </li>
      </ul>

      <h5>2.2 PortScan 攻击</h5>
      <ul>
        <li><strong>攻击描述：</strong>PortScan攻击通过扫描目标网络的开放端口，尝试识别潜在的攻击入口。</li>
        <li><strong>特征分析：</strong>
          <ul>
            <li>端口扫描频繁：多个IP尝试访问不同的端口。</li>
            <li>扫描工具：使用了自动化工具进行高频率端口扫描。</li>
          </ul>
        </li>
      </ul>

      <h5>2.3 DoS_Hulk 攻击</h5>
      <ul>
        <li><strong>攻击描述：</strong>DoS_Hulk通过不断发送HTTP请求来耗尽服务器资源。</li>
        <li><strong>特征分析：</strong>
          <ul>
            <li>请求频率高：短时间内大量HTTP请求。</li>
            <li>单一目标：主要针对特定Web服务。</li>
          </ul>
        </li>
      </ul>

      <h5>2.4 DoS_GoldenEye 攻击</h5>
      <ul>
        <li><strong>攻击描述：</strong>DoS_GoldenEye类似于DoS_Hulk，但具有更复杂的请求策略。</li>
        <li><strong>特征分析：</strong>
          <ul>
            <li>复杂请求：使用多种HTTP头信息进行伪装。</li>
            <li>持久性：请求的持续时间较长，意图使服务不可用。</li>
          </ul>
        </li>
      </ul>

       <li>攻击类型数量</li>
       <div><img src="${require('@/assets/MyData/00.png')}" alt="Image Description" style="width:500px; height: 300px;border-radius: 5px"></div>
       </div>
        <div style="color: #7995f6; font-size: 12px; margin-top: 15px; text-decoration: underline;" id="click-Warning">查看详细情况</div>
`
    };
  },
  methods: {
    handleClick(event) {
      if (event.target && event.target.id === 'click-more') {
        this.Table(); // 调用 Vue 实例中的方法
      }else if(event.target && event.target.id === 'click-download'){
        console.log(66)
        const element = this.fourthReport // 获取需要转换为PDF的DOM元素
        this.$message.success('下载成功！');
        html2pdf().from(element).save('security_report.pdf'); // 将DOM元素转换为PDF并保存
      }else if(event.target && event.target.id === 'click-Warning'){

        this.$router.push('/Warning');
      }
    },
   Table(){
     this.tableShow = !this.tableShow
     console.log(66666)
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
    sendMessage() {
      if (this.userInput.trim() !== '') {
        const userMessage = this.userInput;
        this.chats.push({ type: 'user', message: userMessage });
        this.userInput = '';
        this.scrollToBottom(); // 在用户发送消息后滚动到底部

        if (userMessage === "分析此次威胁报告的信誉情报。") {
          setTimeout(() => {
            this.showIntelligenceReport(0);
          }, 1000);
        } else if(userMessage === "我想要知道此处威胁的溯源链路") {
          setTimeout(() => {
            this.showIntelligenceReport(1);
          }, 1000);
        } else if (userMessage === "我想要查看Linux虚拟机最近一天的网络流量。") {
          this.showIntelligenceReport(2);
        } else if (userMessage === "根据分析结果生成详细的安全威胁情报报告") {
          this.showIntelligenceReport(3);
        }else if (userMessage === "详细分析一下这段时间内出现的威胁攻击") {
          this.showIntelligenceReport(4);
        }
        else {
          setTimeout(this.botResponse, 1000);
        }
      }
    },
    botResponse() {
      const randomIndex = Math.floor(Math.random() * this.responses.length);
      const response = this.responses[randomIndex];
      this.chats.push({ type: 'bot', message: '' }); // 先插入一个空消息
      this.scrollToBottom(); // 在机器人响应后滚动到底部
      this.typeOutMessage(response, this.chats.length - 1);
    },
    showIntelligenceReport(index) {
      // const initialMessage = "以下是关于此次威胁报告的信誉情报分析";
      // this.chats.push({ type: 'bot', message: '' }); // 先插入一个空消息
      this.scrollToBottom();
      // this.typeOutMessage(initialMessage, this.chats.length - 1, () => {
      //   this.displayFullIntelligenceReport();
      // });
      this.displayFullIntelligenceReport(index);
    },
    displayFullIntelligenceReport(index) {
      let report = ''
      if(index===0){
        report = this.intelligenceReport;
      }else if(index===1){
        report = this.secondReport;
      }else if(index===2){
        report = this.threeReport;
      }else if(index===3){
        report = this.fourthReport;
      }else if(index===4){
        report = this.MyDataReport;
      }
      this.chats.push({ type: 'bot', message: '' }); // 插入一条空消息用于报告
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
      }, 10); // 每50ms显示一个字符，调节打字速度
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

.bot-response h4, .bot-response h5 {
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
