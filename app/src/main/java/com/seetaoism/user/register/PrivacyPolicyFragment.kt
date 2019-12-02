package com.seetaoism.user.register

import android.annotation.SuppressLint
import android.view.View
import com.mr.k.mvp.base.BaseFragment
import com.seetaoism.R
import kotlinx.android.synthetic.main.fragment_privacy_policy.*


/*
 * created by Cherry on 2019-11-27
**/
class PrivacyPolicyFragment : BaseFragment(){

    override fun getLayoutId(): Int {
        return R.layout.fragment_privacy_policy
    }

    @SuppressLint("SetTextI18n")
    override fun initView(root: View?) {
        privacy_iv_left_close.setOnClickListener {
            back()
        }
        privacy_tv_content.text = "隐私声明\n" +
                "\n" +
                "    北京十方见道文化传媒有限公司（以下简称本公司）非常重视对您的个人隐私保护，有时候我们需要某些信息才能为您提供您请求的服务。本《隐私声明》（以下简称本声明）向您说明您在使用我们的服务时，我们如何收集、使用、储存和分享这些信息，以及以及我们为您提供的访问、更新、控制和保护这些信息的方式。本声明与您所使用的本公司服务息息相关，我们也希望您能仔细阅读，并在需要时，按照本声明的指引，作出您认为适当的选择。\n" +
                "    本隐私声明适用于本公司的所有相关服务，随着本公司服务范围的扩大，隐私声明的内容可由本公司随时更新，且毋须另行通知。更新后的隐私声明一旦公布即有效代替原来的隐私声明。\n" +
                "\n" +
                "我们收集的信息\n" +
                "通常，您能在匿名的状态下访问“见道”并获取信息。当我们需要能识别您的个人信息或者可以与您联系的信息时，我们会征求您的同意。\n" +
                "您执行的操作和提供的信息。\n" +
                "在您注册我们的账户或申请开通新的功能时，我们可能收集这些信息：姓名、电子邮箱、住址和电话号码等，并征求您的确认。\n" +
                "设备信息。\n" +
                "我们还将根据您授予的权限，从您安装或访问我们服务的电脑、手机或其他设备收集信息或收集与这些设备相关的信息。对于从您的各种设备上收集到的信息，我们可能会将它们进行关联，以便我们能在这些设备上为您提供一致的服务。以下是一些我们收集的设备信息示例：\n" +
                "• 属性，例如操作系统、硬件版本、设备设置、文件和软件名称及类型、电池和信号强度和设备标识符。\n" +
                "• 设备位置，包括特定的地理位置，例如通过 GPS、蓝牙或 WiFi 信号获得的位置信息。\n" +
                "• 连接信息，例如您的移动运营商或网络提供商 (ISP) 名称、浏览器类型、语言和时区、手机号码和 IP 地址。\n" +
                "\n" +
                "关于您的个人信息\n" +
                "本公司严格保护您个人信息的安全。我们使用各种安全技术和程序来保护您的个人信息不被未经授权的访问、使用或泄漏。\n" +
                "本公司会在法律要求或符合本公司的相关服务条款、软件许可使用协议约定的情况下透露您的个人信息，或者有充分理由相信必须这样做才能：\n" +
                "满足法律或行政法规的明文规定，或者符合本公司适用的法律程序；\n" +
                "符合本公司相关服务条款、软件许可使用协议的约定；\n" +
                "保护本公司的权利或财产，以及 在紧急情况下保护本公司员工、本公司产品或服务的用户或大众的个人安全。\n" +
                "本公司不会未经您的允许将这些信息与第三方共享，本声明已经列出的上述情况除外。\n" +
                "\n" +
                "我们如何使用您的信息\n" +
                "我们可能将在向您提供服务的过程之中所收集的信息用作下列用途：\n" +
                "向您提供服务；\n" +
                "在我们提供服务时，用于身份验证、客户服务、安全防范、诈骗监测、存档和备份用途，确保我们向您提供的产品和服务的安全性；\n" +
                "帮助我们设计新服务，改善我们现有服务；\n" +
                "使我们更加了解您如何接入和使用我们的服务，从而针对性地回应您的个性化需求，例如语言设定、位置设定、个性化的帮助服务和指示，或对您和其他使用我们服务的用户作出其他方面的回应；\n" +
                "向您提供与您更加相关的广告以替代普遍投放的广告；\n" +
                "评估我们服务中的广告和其他促销及推广活动的效果，并加以改善；\n" +
                "软件认证或管理软件升级；及\n" +
                "让您参与有关我们产品和服务的调查。\n" +
                "为了让我们的用户有更好的体验、改善我们的服务或您同意的其他用途，在符合相关法律法规的前提下，我们可能将通过我们的某一项服务所收集的个人信息，以汇集信息或者个性化的方式，用于我们的其他服务。例如，在您使用我们的一项服务时所收集的您的个人信息，可能在另一服务中用于向您提供特定内容或向您展示与您相关的、而非普遍推送的信息。如我们在相关服务之中提供了相应选项，您也可以主动要求我们将您在该服务所提供和储存的个人信息用于我们的其他服务。\n" +
                "针对某些特定服务的特定个人信息保护声明将更具体地说明我们在该等服务中如何使用您的信息。\n" +
                "\n" +
                "我们如何分享您的信息\n" +
                "除以下情形外，未经您同意，我们以及我们的关联公司不会与任何第三方分享您的个人信息：\n" +
                "我们以及我们的关联公司可能将您的个人信息与我们的关联公司、合作伙伴及第三方服务供应商分享，用作下列用途：\n" +
                "向您提供我们的服务\n" +
                "实现“我们如何使用您的信息”部分所述目的\n" +
                "履行我们在《用户协议》和《隐私声明》中的义务和行使我们的权利；及\n" +
                "理解、维护和改善我们的服务。\n" +
                "如我们或我们的关联公司与任何上述第三方分享您的个人信息，我们将努力确保在使用您的个人信息时遵守本声明及我们要求其遵守的其他适当的保密和安全措施。\n" +
                "随着我们业务的持续发展，我们以及我们的关联公司有可能进行合并、收购、资产转让或类似的交易，而您的个人信息有可能作为此类交易的一部分而被转移。我们将在您的个人信息转移前通知您。\n" +
                "我们或我们的关联公司还可能为以下需要保留、保存或披露您的个人信息：\n" +
                "遵守适用的法律法规；\n" +
                "遵守法院命令或其他法律程序的规定；\n" +
                "遵守相关政府机关的要求；及\n" +
                "遵守相关政府机关的要求；及\n" +
                "\n" +
                "我们如何保留、储存和保护您的信息\n" +
                "我们仅在本声明所述目的所必需期间和法律法规要求的时限内保留您的个人信息。\n" +
                "我们使用各种安全技术和程序，以防信息的丢失、不当使用、未经授权阅览或披露。例如，在某些服务中，我们将利用加密技术来保护您向我们提供的个人信息。但请您谅解，由于技术的限制以及风险防范的局限，即便我们已经尽量加强安全措施，也无法始终保证信息百分之百的安全。您需要了解，您接入我们的服务所用的系统和通讯网络，有可能因我们可控范围外的情况而发生问题。\n" +
                "Cookie的使用\n" +
                "使用 Cookie 能帮助您实现您的联机体验的个性化，您可以接受或拒绝 Cookie ，大多数 Web 浏览器会自动接受 Cookie，但您通常可根据自己的需要来修改浏览器的设置以拒绝 Cookie。\n" +
                "本公司有时会使用 Cookie 以便知道哪些网页受欢迎，使您在访问“见道”时能得到更好的服务。Cookie不会跟踪个人信息。\n" +
                "当您注册我们的账户时，本公司亦会使用 Cookie。在这种情况下，本公司会收集并存储有用信息，当您再次访问“见道”时，我们可辨认您的身份。来自“见道”的 Cookie 只能被“见道”读取。\n" +
                "如果您的浏览器被设置为拒绝 Cookie，您仍然能够访问“见道”的大多数网页。\n" +
                "\n" +
                "我们向您发送的邮件和信息\n" +
                "邮件和信息推送\n" +
                "您使用我们服务时，我们可能使用您的信息向您的设备发送电子邮件、新闻或推送通知。如您不希望收到这些信息，您可以按照我们向您发出的电子邮件所述指示，在设备上选择取消订阅。\n" +
                "与服务有关的公告\n" +
                "我们可能在必需时（例如当我们由于系统维护而暂停某一项服务时）向您发出与服务有关的公告。您可能无法取消这些与服务有关、性质不属于推广的公告。\n" +
                "\n" +
                "我们服务中的第三方服务\n" +
                "我们的服务可能包括或链接至第三方提供的社交媒体或其他服务（包括网站）。例如：\n" +
                "您可利用“分享”键将某些内容分享到我们的服务，或您可利用第三方连线服务登录我们的服务。这些功能可能会收集您的信息（包括您的日志信息），并可能在您的电脑装置Cookies，从而正常运行上述功能；及\n" +
                "我们通过广告或我们服务的其他方式向您提供链接，使您可以接入第三方的服务或网站。\n" +
                "该等第三方社交媒体或其他服务可能由相关的第三方或我们运营。您使用该等第三方的社交媒体服务或其他服务（包括您向该等第三方提供的任何个人信息），须受第三方自己的服务条款及隐私声明（而非本公司《用户协议》或本《隐私声明》）约束，您需要仔细阅读其条款。本声明仅适用于我们所收集的任何信息，并不适用于任何第三方提供的服务或第三方的信息使用规则，而我们对任何第三方使用由您提供的信息不承担任何责任。\n" +
                "\n" +
                "北京十方见道文化传媒有限公司"
    }

    override fun cancelRequest() {
    }


}