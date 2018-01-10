cd ../../
./gradlew assemble
cd app/build/outputs/apk/release
mv app-release-unsigned.apk vmarketplace.apk
scp vmarketplace.apk mj2eu@power1.cs.virginia.edu:/zf5/mj2eu/public_html/apps/vmarketplace/

