(defproject test-lanterna "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.10.2-alpha2"]
                 [com.googlecode.lanterna/lanterna "3.0.3"]]
  :plugins [[io.taylorwood/lein-native-image "0.3.1"]]

  :native-image {:opts ["-H:Name=test-lanterna"
                        "-H:+ReportExceptionStackTraces"
                        "-J-Dclojure.spec.skip-macros=true"
                        "-J-Dclojure.compiler.direct-linking=true"
                        "-H:IncludeResources=BABASHKA_VERSION"
                        "-H:IncludeResources=SCI_VERSION"
                        ;; "--initialize-at-build-time=java.awt.color.ICC_ColorSpace"
                        "--initialize-at-build-time"
                        "-H:Log=registerResource:"
                        "-H:EnableURLProtocols=http,https,jar"
                        "--enable-all-security-services"
                        "-H:+JNI"
                        "--verbose"
                        "--no-fallback"
                        "--no-server"
                        "--report-unsupported-elements-at-runtime"
                        "--initialize-at-run-time=org.postgresql.sspi.SSPIClient"
                        "--initialize-at-run-time=sun.java2d.xr.XRBackendNative"
                        "--initialize-at-run-time=sun.awt.X11GraphicsConfig"
                        "--initialize-at-run-time=sun.awt.dnd.SunDropTargetContextPeer$EventDispatcher"]}
   :uberjar {:aot :all
             :jvm-opts ["-Dclojure.compiler.direct-linking=true"]
             :resource-paths ["resources"]}
  :main ^:skip-aot test-lanterna.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
