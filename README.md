# O2OBackend
O2O Backend Management System

## 克隆现有的仓库
如果你想获得一份已经存在了的 Git 仓库的拷贝，比如说，你想为某个项目贡献自己的一份力，这时就要用
到 git clone 命令。

克隆仓库的命令格式是 git clone [url] 。比如，要克隆 Git 的可链接库 O2OBackend，可以用下面的命令：
```
git clone git@gitee.com:avichen/O2OBackend.git
```
这会在当前目录下创建一个名为 “O2OBackend” 的目录，并在这个目录下初始化一个 .git 文件夹，从远程仓库拉
取下所有数据放入 .git 文件夹，然后从中读取最新版本的文件的拷贝。如果你进入到这个新建的 O2OBackend 文
件夹，你会发现所有的项目文件已经在里面了，准备就绪等待后续的开发和使用。

请记住，你工作目录下的每一个文件都不外乎这两种状态：已跟踪或未跟踪。已跟踪的文件是指那些被纳入了版
本控制的文件，在上一次快照中有它们的记录，在工作一段时间后，它们的状态可能处于未修改，已修改或已放
入暂存区。工作目录中除已跟踪文件以外的所有其它文件都属于未跟踪文件，它们既不存在于上次快照的记录
中，也没有放入暂存区。初次克隆某个仓库的时候，工作目录中的所有文件都属于已跟踪文件，并处于未修改状
态。

编辑过某些文件之后，由于自上次提交后你对它们做了修改，Git 将它们标记为已修改文件。我们逐步将这些修
改过的文件放入暂存区，然后提交所有暂存了的修改，如此反复。所以使用 Git 时文件的生命周期如下：
![](https://git-scm.com/book/en/v2/images/lifecycle.png)

### 检查当前文件状态
要查看哪些文件处于什么状态，可以用 git status 命令。如果在克隆仓库后立即使用此命令，会看到类似这
样的输出：
```
git status
On branch dev
Your branch is up-to-date with 'origin/dev'.

nothing to commit, working tree clean
```
这说明你现在的工作目录相当干净。换句话说，所有已跟踪文件在上次提交后都未被更改过。现在，分支名是 “dev”,这是(该项目的)默认的分支名。一般项目默认分支名为“master”

### 跟踪新文件 || 暂存已修改文件 || 把有冲突的文件标记为已解决状态
```
git add (files)
```
git add 命令使用文件或目录的路径作为参数；如果参数是目录的路径，该
命令将递归地跟踪该目录下的所有文件。
将这个
命令理解为“添加内容到下一次提交中”而不是“将一个文件添加到项目中”要更加合适。

### 查看已暂存和未暂存的修改
如果 git status 命令的输出对于你来说过于模糊，你想知道具体修改了什么地方，可以用 git diff 命令。
#### 查看未暂存的修改
```
git diff
```
#### 查看已暂存的修改
```
git diff cached
```

### 提交更新
现在的暂存区域已经准备妥当可以提交了。在此之前，请一定要确认还有什么修改过的或新建的文件还没有 git
add 过，否则提交的时候不会记录这些还没暂存起来的变化。这些修改过的文件只保留在本地磁盘。所以，每次
准备提交前，先用 git status 看下，是不是都已暂存起来了，然后再运行提交命令 git commit:
```
git commit -m "本次提交更新文件说明"
```

#### (跳过使用暂存区域)
Git 提供了一个跳过使用暂存
区域的方式，只要在提交的时候，给 git commit 加上 -a 选项，Git 就会自动把所有已经跟踪过的文件暂存起
来一并提交，从而跳过 git add 步骤:
```
git commit -am "本次提交更新文件说明"
```

### 移除文件 (下一次提交时，该文件就不再纳入版本管理了。)
我们想把文件从 Git 仓库中删除（亦即从暂存区域移除），但仍然希望保留在当前工作目录
中。换句话说，你想让文件保留在磁盘，但是并不想让 Git 继续跟踪。
```
git rm --cached (files)
```
从已跟踪文件清单中移除（确切地说，是从暂存区域移除）并连带从工作目录中删除指定的文件。
```
git rm (files)
```
如果只是简单地从工作目录中手工删除文件，运行 git status 时就会在 “Changes not staged for
commit” 部分（也就是 未暂存清单）看到:
```
rm (files)
```

### 移动文件 || 文件重命名
```
git mv file_from file_to
```
其实，运行 git mv 就相当于运行了下面三条命令：
```
mv README.md README
git rm README.md
git add README
```
如此分开操作，Git 也会意识到这是一次改名，所以不管何种方式结果都一样。两者唯一的区别是，mv 是一条命
令而另一种方式需要三条命令，直接用 git mv 轻便得多。不过有时候用其他工具批处理改名的话，要记得在提
交前删除老的文件名，再添加新的文件名。


## 远程仓库的使用
## 克隆远程仓库到本地
如果你想获得一份已经存在了的 Git 仓库的拷贝，比如说，你想为某个项目贡献自己的一份力，这时就要用
到 git clone 命令。

克隆仓库的命令格式是 git clone [url] 。比如，要克隆 Git 的可链接库 O2OBackend，可以用下面的命令：
```
git clone git@gitee.com:avichen/O2OBackend.git
```

### 查看远程仓库
如果想查看你已经配置的远程仓库服务器，可以运行 git remote 命令。它会列出你指定的每一个远程服务器
的简写。如果你已经克隆了自己的仓库，那么至少应该能看到 origin - 这是 Git 给你克隆的仓库服务器的默认名
字;你也可以指定选项 -v，会显示需要读写远程仓库使用的 Git 保存的简写与其对应的 URL。
```
git remote -v
origin  https://gitee.com/avichen/O2OBackend.git (fetch)
origin  https://gitee.com/avichen/O2OBackend.git (push)
```

### 从远程仓库中抓取
如果你有一个分支设置为跟踪一个远程分支（阅读下一节与 Git 分支 了解更多信息），可以使用 git pull 命
令来自动的抓取然后合并远程分支到当前分支。这对你来说可能是一个更简单或更舒服的工作流程；默认情况
下，git clone 命令会自动设置本地 master 分支跟踪克隆的远程仓库的 master 分支（或不管是什么名字的默
认分支）。运行 git pull 通常会从最初克隆的服务器上抓取数据并自动尝试合并到当前所在的分支。
```
git pull
Already up-to-date.
```

### 推送到远程仓库
```
git push origin dev
```
当你和其他人在同一时间克隆，他们先推送到上游然后你再推送到上游，你的推送就会毫无疑问地被拒绝。
你必须先将他们的工作拉取下来并将其合并进你的工作后才能推送。


## git 分支
### 查看本地分支  
[ -r 查看远程分支  -a 查看本地与远程分支 -v 查看每一个分支最后一次提交  
--merged 查看当前分支已合并的分支  --no-merged 查看所有包含未合并工作的分支 ]
```
git branch -a
* dev
  remotes/origin/HEAD -> origin/dev
  remotes/origin/V1.0
  remotes/origin/dev
  remotes/origin/master
```

###  创建分支
```
git branch iss01

git branch 
* dev
  iss01
``` 
### 切换分支
```
git checkout iss01

git branch 
  dev
* iss01
```

### 创建分支并切换分支
```
git checkout -b iss02

git branch
  dev
  iss01
* iss02
```

### 合并分支 (将分支数据合并到当前分支)
```
git merge iss01
```

### 删除分支
[-D 选项强制删除]
```
git branch -d iss01
git branch -d iss02
```

### 跟踪分支
从一个远程跟踪分支检出一个本地分支会自动创建一个叫做 “跟踪分支”（有时候也叫做 “上游分支”）。
跟踪分支是与远程分支有直接关系的本地分支。
这是一个十分常用的操作所以 Git 提供了 --track 快捷方式
```
git checkout -b master origin/master
git checkout --track origin/master
```

