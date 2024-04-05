package com.hesh925.mcprometheus.prometheus.Meters;

import com.hesh925.mcprometheus.MCPrometheus;
import io.prometheus.metrics.core.metrics.Counter;
import io.prometheus.metrics.core.metrics.Gauge;
import io.prometheus.metrics.core.metrics.Info;
import io.prometheus.metrics.instrumentation.jvm.JvmMetrics;

public class JREExporter {
    private final MCPrometheus plugin;
    private final String prefix;

    public JREExporter(MCPrometheus instance) {
        this.plugin = instance;
        this.prefix = this.plugin.config.getString("Exporter.PromPrefix");

        JvmMetrics.builder().register();

        if(System.getProperty("java.runtime.version") != null) {
            plugin.getLogger().warning("java.runtime.version is null");
            return;
        }


        Info info = Info.builder()
                .name(prefix + "jvm_runtime_info")
                .help("JVM runtime info")
                .labelNames("os", "version", "vendor", "runtime")
                .register();
        String os = System.getProperty("os.name", "unknown");
        String version = System.getProperty("java.vm.version", "unknown");
        String vendor = System.getProperty("java.vm.vendor", "unknown");
        String runtime = System.getProperty("java.runtime.name", "unknown");
        info.setLabelValues(os, version, vendor, runtime);

    }
}
