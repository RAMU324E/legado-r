# 阅读R

基于原项目 [gedoor/legado](https://github.com/gedoor/legado) 修改的本地阅读器版本。

## 说明

- 仅保留当前可用的最新版本快照
- 主要面向本地阅读与自定义朗读使用
- 适合日语 EPUB、注音显示与在线 TTS 场景

## 主要修改

- 修复日语 EPUB `ruby / 注音` 显示
- 修复多字注音错位、偏移、只挂第一个字的问题
- 朗读时支持注音优先，避免汉字与注音重复朗读
- 尽量不影响普通中文阅读
- 兼容返回 `JSON + base64` 音频的在线 TTS
- 支持 `MiMo TTS`
- 支持 `xAI / Grok TTS`

## 在线 TTS 示例

## 简单教程

进入：

- 朗读
- 朗读设置
- 在线朗读引擎
- 新建或编辑一个引擎

没写到的项目直接留空。

### MiMo TTS

填写示例：

```text
url：
https://api.xiaomimimo.com/v1/chat/completions,{"method":"POST","body":{"model":"mimo-v2-tts","messages":[{"role":"assistant","content":{{JSON.stringify(speakText)}}}],"audio":{"format":"wav","voice":"default_zh"}}}

请求头（header）：
{"Authorization":"Bearer 你的 MIMO_API_KEY","Content-Type":"application/json"}
```

### xAI / Grok TTS

填写示例：

```text
url：
https://api.x.ai/v1/tts,{"method":"POST","body":{"text":{{JSON.stringify(speakText)}},"voice_id":"eve","language":"auto"}}

Content-Type：
audio/mpeg

请求头（header）：
{"Authorization":"Bearer 你的 xAI API Key","Content-Type":"application/json"}
```

## 下载

- 安装包见仓库右侧 `Releases`

## 免责声明

仅供本地阅读器相关的 UI 设计学习交流，请勿用于任何违法犯罪行为。

由使用者造成的任何违法犯罪活动均与作者无关。

请于下载后 24 小时内删除。
