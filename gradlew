#!/bin/sh
# Resolve this script's directory so the wrapper also works outside the project.
APP_HOME=$(CDPATH='' cd -- "$(dirname -- "$0")" && pwd) || exit 1
if [ -n "$JAVA_HOME" ]; then
    JAVACMD="$JAVA_HOME/bin/java"
else
    JAVACMD=java
fi
if ! command -v "$JAVACMD" >/dev/null 2>&1; then
    echo 'Java 21 is required. Set JAVA_HOME or add java to PATH.' >&2
    exit 1
fi
exec "$JAVACMD" -Xms64m -Xmx64m $JAVA_OPTS $GRADLE_OPTS \
    -Dorg.gradle.appname=gradlew \
    -classpath "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" \
    org.gradle.wrapper.GradleWrapperMain "$@"
