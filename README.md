
##MVP架构
- M : 还是业务层和模型层
- V  : 视图层的责任由Activity来担当
- P : 新成员Prensenter 用来代理 C(control) 控制层

#MVP通用类
新增了MVP通用类，更少的代码，更好使用
* BaseView
* BasePresenter
* BaseActivity

#添加代码步骤
 * 通过Template生成对应页面的MVP和Dagger代码,请注意输入框中输入的名字必须相同
 * 由于每个项目包结构都不一定相同,所以每生成一个文件需要自己导入import包名,可以在设置中设置自动导入包名
 * 请在对应包下按以下顺序生成对应代码,Contract->Model->Presenter->Activity->Module->Component
 * 因为生成Activity时,Module和Component还没生成,但是Activity中有它们的引用,所以会报错,但是不用理会
 * 继续将Module和Component生成完后,编译一下项目再回到Activity,按提示修改一个方法名即可
 * 如果想生成Fragment的相关文件,则将上面构建顺序中的Activity换为Fragment,并将Component中inject方法的参数改为此Fragment

框架参考文章：
Dagger2从入门到放弃再到恍然大悟
http://www.jianshu.com/p/39d1df6c877d
Android Dagger2 MVP架构 一看就明白
http://blog.csdn.net/soslinken/article/details/52184113
Android：dagger2让你爱不释手-重点概念讲解、融合篇
http://www.jianshu.com/p/1d42d2e6f4a5