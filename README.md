# SC2002 - Hospital Management System 

## Git commands
### BASICS
Cloning this repo to your computer:
* git clone https://github.com/GabrielLim01/SC2002-Project.git

Pulling new changes (Make sure you are on the correct branch):

* git pull

Pushing new changes:

* git add .
* git commit -m "\<your message here>"
* git push

Switching to another branch:

* git checkout \<branch name>

**If you want to work on a new feature (IMPORTANT):**

* git checkout -b \<Name of feature>
* git add .
* git commit -m "\<your message here>"
* git push
* git push -u origin \<Name of feature>

Updating your local list of branches based on the remote repo:

* git fetch

Viewing your local list of branches (Both local and remote):

* git branch -a

If you want to check whether your current branch is up-to-date or not:
* git status

### INTERMEDIATE
If you are coding on a branch halfway but need to switch to another branch for some reason:<br />
(You can also do this if you are coding halfway and want to reference the original version of the current branch)
* git stash
* git stash pop (to reverse)

If you accidentally committed some changes and want to wipe them out completely:

* git reset --hard

If there is a merging conflict and I am not around to resolve it (This should not happen ideally):

* git merge \<branch name TO BE MERGED INTO MAIN> **(MAKE SURE YOU ARE ON THE MAIN BRANCH)**

If you see a funny-looking blue text screen and don't know what to do, refer to this: https://stackoverflow.com/questions/19085807/please-enter-a-commit-message-to-explain-why-this-merge-is-necessary-especially

Merging automatically does the add and commit commands for you, so simply run a git push to commit those changes into the remote repo.

### ADVANCED
When you push code to GitHub, Git sometimes adds line-breaks to your code depending on your autocrlf setting. This can be very ugly and mess up formatting for others who pull your code, so let's standardize everyone's settings to FALSE by default.

Setting line-endings in git to false by default:
* Check your autocrlf status here: https://stackoverflow.com/questions/1475199/how-can-i-print-out-the-value-of-a-git-configuration-setting-core-autocrlf-on
(If it says false or doesn't display anything, it is already set to false and you don't have to do anything)
* Change your autocrlf status here: https://stackoverflow.com/questions/10418975/how-to-change-line-ending-settings
* More info: https://stackoverflow.com/questions/2825428/why-should-i-use-core-autocrlf-true-in-git

### OTHERS

(I haven't used these commands much but they exist)
- git rebase
- git cherry-pick