@echo off
echo.
echo [AIDOCSPRO OCR] GIT CLEANUP SCRIPT
echo ----------------------------------

echo 1. Removing JAR from Git index...
git rm --cached target/aidocspro-ocr-backend-1.0.0.jar

echo 2. Updating .gitignore...
echo target/>>.gitignore
echo *.jar>>.gitignore
git add .gitignore
git commit -m "🔥 Trvalé ignorovanie .jar a target/"

echo 3. Removing JAR from Git history using git-filter-repo...
python "C:/Users/lukac/Desktop/repo py/repo/git_filter_repo.py" --path target/aidocspro-ocr-backend-1.0.0.jar --invert-paths --force

echo 4. Adding remote and pushing to GitHub...
git remote add origin https://github.com/BantVegas/aidocspro-ocr.git
git push origin main --force

echo.
echo ✅ Cleanup complete. Project is ready for GitHub push.
pause
