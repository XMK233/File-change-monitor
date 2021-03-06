Readme
1.	文件是ArkhamOriginMain1.java
2.	本程序在win10下编写，请务必在windows环境下测试本程序。Win下文件读入不分大小写，无需另外说明，所以请不要在别的操作系统下运行本程序，否则产生的错误本人不认可。(假如您是苹果用户且没装windows，那么您可以在周末的白天到实验楼5楼机房，用那里的电脑测试)
3.	监控的文件大小请不要大于1.5GB。
4.	本程序所有的操作只涉及文件和文件夹这两类对象，如果不是这两个中的东西不予处理。
5.	文件读入不区分大小写，但是不意味着测试人员输入的文件或路径名称可以随意输入。比如随意忽略空白符或者增添空白符导致文件名称或路径变无效，这样导致的错误本人不认可。
6.	请不要一上来就Ctrl+z，构建和谐6系人人有责。
7.	最开始输入的是一个范围，请务必输入一个目录文件夹即顶层目录，如K:\Users\Hunter Hugh\Desktop\Newfolder这代表了所有的文件和一切相关操作都只会在这个目录之下完成，超出这个目录的文件不予操作。如果违背这个要求导致错误，本人不接受。特别注意：被监控的文件移动到了这个目录之下的任意位置，就会触发相关触发器。还有，请不要在地址上做文章，这么所没有意义。由于测试者输入有误导致这样的错误本人不予认可。
8.	先输入所要监控的东西，文件名之类的，最多5个，一旦满了5个合法请求就自动开开始后面的操作，不满就输入end结束。开始运行后就一直监控这些东西直到被监控对象消失，到那时此线程才结束。
9.	文件读写线程安全类使用方法：每对一个文件或者目录进行操作，本线程就会暂停一段时间，完成所有trigger的扫描。
10.	输入格式：IF |目录| 触发器 THEN 任务，例如不含前面的冒号和最后的逗号，输入的文件或目录名应该在||中。本次作业考察的重点不是正则表达式，所以请测试者不要在这里做文章，这样做没有任何意义。务必输入符合windows命名规范的、完整的绝对地址信息，不要增删没有意义的符号。括号中地址所示的目录或文件可能是不存在的，但不能是不合法的。这点跟基本法有所不同，但在基本法中这里不是红字或硬性规定，所以特在此说明，以本文档为准。
11.	//请不要访问中文目录，基本法中虽有说明访问中文目录的问题但是不强制，故以本文所要求的为准。
12.	如果是目录需要计算其大小，则目录的大小是windows下该文件夹属性里的大小而非占用空间。本程序中这里是有递归的。如果监控文件夹的大小变化，消除歧义说明中说明的情况在本程序中被包含，即测试者在本目录下（不递归）修改了文件会触发，但是如果监控的目录下的文件夹下的文件修改了，也会触发。所以这里请不要误判为错误。
13.	初始的时候，目录的最近修改时间是直接自系统获得的修改时间，如果后来目录下有一个文件修改了，那么这个目录的最近修改时间改为最近被修改的目录或文件的修改时间。
14.	这里定义的目录的修改时间是指在某一时刻检测得到的其下最近一次修改的文件的修改时间。
15.	这里定义的文件位置的改变，是这样定义的：一个地方删除，顶层目录下另一个地方出现跟删掉的文件性质完全一样的文件。如果有多个文件都是和删掉的文件性质相同，那么排在前面的目录下的排在前面的这样的文件会被先查到，位移的目的就被认为是这里。
16.	输入的顶层目录中最多只能包含100个文件(夹)，否则强制要求测试者重新输入。
17.	先输入的请求有优先判断的权利。
18.	请不要输入一开始就不存在的目录。这样的话线程不会追踪。请务必在输入监控目标的时候输入存在的文件/目录的地址。
19.	输入的时候务必一条一条输入，即输入一行就敲一次回车。
20.	本程序中的文件不保证recover后的文件的最后修改时间和原来的一样。
21.	如果触发器监测的文件或目录消失，线程终止。只要文件或目录不满足基本法中规定的任一条件，就当它消失了。比如：文件改名触发器，如果这个文件被改名同时其内容被修改，那么违背了判断文件改名的原则，原本监测着的文件被认定是消失了，该线程终止。
22.	如果文件夹下的文件修改了文件名，会导致该文件夹的修改时间发生变化。本程序处理文件夹修改时间的原则是底下的所有目录和文件只要有发生修改时间变化的，检测的目录就发生了变化，并不是直接对该文件夹直接获取修改时间。比如在目标文件夹下的文件夹下修改了某个文件，导致这个文件的修改时间发生变化；在目标文件夹下新建目录或文件，都会导致目标文件夹下的文件或文件夹的修改时间发生变化，从而使得目标文件夹的修改时间变化。特别的，在某个文件夹中修改某个文件如word文档（本文从此以后将这类文件简称为word或word文档）后，这个文件的上层目录修改时间会发生一次变化；打开或关闭这个文件，其上层的目录的修改时间会改变，这个变动会被反映到目标文件夹的修改时间中。所以这里比较特别，特此提醒。据说这是因为word等类型的文件打开关闭会产生一些临时文件，会改变文件夹的修改时间。
23.	由于word文档等文件的特殊机制，它们的打开关闭增删修改等操作都会导致修改时间和大小的微小变化，所以当检测的对象文件是word，或是测试的操作涉及对word的修改时，会导致一些看似意外的结果，但这是word的机制所致，乃客观因素。.txt貌似就没有这类问题。
24.	多说一句，word可能还有一些奇怪的性质，比如说新建的时候大小是0，增加字符后清空，大小不能恢复到0。这些性质可能会导致测试者的误判。
25.	本程序不支持文件夹的重命名和移动功能。例如：如果出现IF (K:\Users\Hunter Hugh\Desktop\Newfolder\New folder1\New folder) renamed THEN record-summary 或者IF (K:\Users\Hunter Hugh\Desktop\Newfolder\New folder1\New folder) path-changed THEN record-summary 被认为是无效的。基本法中有提到，但这里不是强制规定，所以特此说明，以本文档为准。
26.	输出的summary是这个触发器触发的次数。Detail是相应触发器记录的上次到这次的变化。Trigger n这个n是指线程的名字，是数字，按照输入合法输入的顺序排列。所有的信息输出在Result.txt里，这个文件在你最开始输入的那个最大的顶层目录下。输出的文件包含的内容是触发器触发的次数，以及触发器触发时的文件变化情况。
27.	触发器扫描频率为10秒左右。测试者线程每做出一个修改会休息1分钟。测试者线程没有完成，测试者可根据注释，结合自己喜好的输入方式编写完整，代码的563-567行是参考的测试者线程的位置。


