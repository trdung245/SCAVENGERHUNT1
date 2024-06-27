package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.MinimalExternalModuleDependency;
import org.gradle.plugin.use.PluginDependency;
import org.gradle.api.artifacts.ExternalModuleDependencyBundle;
import org.gradle.api.artifacts.MutableVersionConstraint;
import org.gradle.api.provider.Provider;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.api.internal.catalog.AbstractExternalDependencyFactory;
import org.gradle.api.internal.catalog.DefaultVersionCatalog;
import java.util.Map;
import org.gradle.api.internal.attributes.ImmutableAttributesFactory;
import org.gradle.api.internal.artifacts.dsl.CapabilityNotationParser;
import javax.inject.Inject;

/**
 * A catalog of dependencies accessible via the {@code libs} extension.
 */
@NonNullApi
public class LibrariesForLibs extends AbstractExternalDependencyFactory {

    private final AbstractExternalDependencyFactory owner = this;
    private final AndroidxLibraryAccessors laccForAndroidxLibraryAccessors = new AndroidxLibraryAccessors(owner);
    private final BitcoinjLibraryAccessors laccForBitcoinjLibraryAccessors = new BitcoinjLibraryAccessors(owner);
    private final ConverterLibraryAccessors laccForConverterLibraryAccessors = new ConverterLibraryAccessors(owner);
    private final IpfsLibraryAccessors laccForIpfsLibraryAccessors = new IpfsLibraryAccessors(owner);
    private final JavaLibraryAccessors laccForJavaLibraryAccessors = new JavaLibraryAccessors(owner);
    private final MmorrellLibraryAccessors laccForMmorrellLibraryAccessors = new MmorrellLibraryAccessors(owner);
    private final P2pLibraryAccessors laccForP2pLibraryAccessors = new P2pLibraryAccessors(owner);
    private final SolanajLibraryAccessors laccForSolanajLibraryAccessors = new SolanajLibraryAccessors(owner);
    private final VersionAccessors vaccForVersionAccessors = new VersionAccessors(providers, config);
    private final BundleAccessors baccForBundleAccessors = new BundleAccessors(objects, providers, config, attributesFactory, capabilityNotationParser);
    private final PluginAccessors paccForPluginAccessors = new PluginAccessors(providers, config);

    @Inject
    public LibrariesForLibs(DefaultVersionCatalog config, ProviderFactory providers, ObjectFactory objects, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) {
        super(config, providers, objects, attributesFactory, capabilityNotationParser);
    }

    /**
     * Dependency provider for <b>junit</b> with <b>junit:junit</b> coordinates and
     * with version reference <b>junit</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getJunit() {
        return create("junit");
    }

    /**
     * Dependency provider for <b>material</b> with <b>com.google.android.material:material</b> coordinates and
     * with version reference <b>material</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getMaterial() {
        return create("material");
    }

    /**
     * Dependency provider for <b>retrofit</b> with <b>com.squareup.retrofit2:retrofit</b> coordinates and
     * with version reference <b>converterGson</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getRetrofit() {
        return create("retrofit");
    }

    /**
     * Dependency provider for <b>solana</b> with <b>com.solana:solana</b> coordinates and
     * with version reference <b>solana</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getSolana() {
        return create("solana");
    }

    /**
     * Group of libraries at <b>androidx</b>
     */
    public AndroidxLibraryAccessors getAndroidx() {
        return laccForAndroidxLibraryAccessors;
    }

    /**
     * Group of libraries at <b>bitcoinj</b>
     */
    public BitcoinjLibraryAccessors getBitcoinj() {
        return laccForBitcoinjLibraryAccessors;
    }

    /**
     * Group of libraries at <b>converter</b>
     */
    public ConverterLibraryAccessors getConverter() {
        return laccForConverterLibraryAccessors;
    }

    /**
     * Group of libraries at <b>ipfs</b>
     */
    public IpfsLibraryAccessors getIpfs() {
        return laccForIpfsLibraryAccessors;
    }

    /**
     * Group of libraries at <b>java</b>
     */
    public JavaLibraryAccessors getJava() {
        return laccForJavaLibraryAccessors;
    }

    /**
     * Group of libraries at <b>mmorrell</b>
     */
    public MmorrellLibraryAccessors getMmorrell() {
        return laccForMmorrellLibraryAccessors;
    }

    /**
     * Group of libraries at <b>p2p</b>
     */
    public P2pLibraryAccessors getP2p() {
        return laccForP2pLibraryAccessors;
    }

    /**
     * Group of libraries at <b>solanaj</b>
     */
    public SolanajLibraryAccessors getSolanaj() {
        return laccForSolanajLibraryAccessors;
    }

    /**
     * Group of versions at <b>versions</b>
     */
    public VersionAccessors getVersions() {
        return vaccForVersionAccessors;
    }

    /**
     * Group of bundles at <b>bundles</b>
     */
    public BundleAccessors getBundles() {
        return baccForBundleAccessors;
    }

    /**
     * Group of plugins at <b>plugins</b>
     */
    public PluginAccessors getPlugins() {
        return paccForPluginAccessors;
    }

    public static class AndroidxLibraryAccessors extends SubDependencyFactory {
        private final AndroidxCoreLibraryAccessors laccForAndroidxCoreLibraryAccessors = new AndroidxCoreLibraryAccessors(owner);
        private final AndroidxEspressoLibraryAccessors laccForAndroidxEspressoLibraryAccessors = new AndroidxEspressoLibraryAccessors(owner);

        public AndroidxLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>activity</b> with <b>androidx.activity:activity</b> coordinates and
         * with version reference <b>activity</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getActivity() {
            return create("androidx.activity");
        }

        /**
         * Dependency provider for <b>appcompat</b> with <b>androidx.appcompat:appcompat</b> coordinates and
         * with version reference <b>appcompat</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAppcompat() {
            return create("androidx.appcompat");
        }

        /**
         * Dependency provider for <b>constraintlayout</b> with <b>androidx.constraintlayout:constraintlayout</b> coordinates and
         * with version reference <b>constraintlayout</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getConstraintlayout() {
            return create("androidx.constraintlayout");
        }

        /**
         * Dependency provider for <b>junit</b> with <b>androidx.test.ext:junit</b> coordinates and
         * with version reference <b>junitVersion</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJunit() {
            return create("androidx.junit");
        }

        /**
         * Group of libraries at <b>androidx.core</b>
         */
        public AndroidxCoreLibraryAccessors getCore() {
            return laccForAndroidxCoreLibraryAccessors;
        }

        /**
         * Group of libraries at <b>androidx.espresso</b>
         */
        public AndroidxEspressoLibraryAccessors getEspresso() {
            return laccForAndroidxEspressoLibraryAccessors;
        }

    }

    public static class AndroidxCoreLibraryAccessors extends SubDependencyFactory {

        public AndroidxCoreLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>ktx</b> with <b>androidx.core:core-ktx</b> coordinates and
         * with version reference <b>coreKtx</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getKtx() {
            return create("androidx.core.ktx");
        }

    }

    public static class AndroidxEspressoLibraryAccessors extends SubDependencyFactory {

        public AndroidxEspressoLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>core</b> with <b>androidx.test.espresso:espresso-core</b> coordinates and
         * with version reference <b>espressoCore</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCore() {
            return create("androidx.espresso.core");
        }

    }

    public static class BitcoinjLibraryAccessors extends SubDependencyFactory {

        public BitcoinjLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>core</b> with <b>org.bitcoinj:bitcoinj-core</b> coordinates and
         * with version reference <b>bitcoinjCore</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCore() {
            return create("bitcoinj.core");
        }

    }

    public static class ConverterLibraryAccessors extends SubDependencyFactory {

        public ConverterLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>gson</b> with <b>com.squareup.retrofit2:converter-gson</b> coordinates and
         * with version reference <b>converterGson</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getGson() {
            return create("converter.gson");
        }

    }

    public static class IpfsLibraryAccessors extends SubDependencyFactory {
        private final IpfsJavaLibraryAccessors laccForIpfsJavaLibraryAccessors = new IpfsJavaLibraryAccessors(owner);

        public IpfsLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>ipfs.java</b>
         */
        public IpfsJavaLibraryAccessors getJava() {
            return laccForIpfsJavaLibraryAccessors;
        }

    }

    public static class IpfsJavaLibraryAccessors extends SubDependencyFactory {
        private final IpfsJavaIpfsLibraryAccessors laccForIpfsJavaIpfsLibraryAccessors = new IpfsJavaIpfsLibraryAccessors(owner);

        public IpfsJavaLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>ipfs.java.ipfs</b>
         */
        public IpfsJavaIpfsLibraryAccessors getIpfs() {
            return laccForIpfsJavaIpfsLibraryAccessors;
        }

    }

    public static class IpfsJavaIpfsLibraryAccessors extends SubDependencyFactory {
        private final IpfsJavaIpfsHttpLibraryAccessors laccForIpfsJavaIpfsHttpLibraryAccessors = new IpfsJavaIpfsHttpLibraryAccessors(owner);

        public IpfsJavaIpfsLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>ipfs.java.ipfs.http</b>
         */
        public IpfsJavaIpfsHttpLibraryAccessors getHttp() {
            return laccForIpfsJavaIpfsHttpLibraryAccessors;
        }

    }

    public static class IpfsJavaIpfsHttpLibraryAccessors extends SubDependencyFactory {

        public IpfsJavaIpfsHttpLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>client</b> with <b>com.github.ipfs:java-ipfs-http-client</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getClient() {
            return create("ipfs.java.ipfs.http.client");
        }

    }

    public static class JavaLibraryAccessors extends SubDependencyFactory {
        private final JavaIpfsLibraryAccessors laccForJavaIpfsLibraryAccessors = new JavaIpfsLibraryAccessors(owner);

        public JavaLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>java.ipfs</b>
         */
        public JavaIpfsLibraryAccessors getIpfs() {
            return laccForJavaIpfsLibraryAccessors;
        }

    }

    public static class JavaIpfsLibraryAccessors extends SubDependencyFactory {
        private final JavaIpfsHttpLibraryAccessors laccForJavaIpfsHttpLibraryAccessors = new JavaIpfsHttpLibraryAccessors(owner);

        public JavaIpfsLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>java.ipfs.http</b>
         */
        public JavaIpfsHttpLibraryAccessors getHttp() {
            return laccForJavaIpfsHttpLibraryAccessors;
        }

    }

    public static class JavaIpfsHttpLibraryAccessors extends SubDependencyFactory {

        public JavaIpfsHttpLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>client</b> with <b>io.ipfs:java-ipfs-http-client</b> coordinates and
         * with version reference <b>javaIpfsHttpClient</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getClient() {
            return create("java.ipfs.http.client");
        }

    }

    public static class MmorrellLibraryAccessors extends SubDependencyFactory {

        public MmorrellLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>solanaj</b> with <b>com.mmorrell:solanaj</b> coordinates and
         * with version reference <b>mmorrellSolanaj</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getSolanaj() {
            return create("mmorrell.solanaj");
        }

    }

    public static class P2pLibraryAccessors extends SubDependencyFactory {

        public P2pLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>solanaj</b> with <b>org.p2p:solanaj</b> coordinates and
         * with version reference <b>solanajVersion</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getSolanaj() {
            return create("p2p.solanaj");
        }

    }

    public static class SolanajLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public SolanajLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>solanaj</b> with <b>com.github.skynetcap:solanaj</b> coordinates and
         * with version reference <b>solanaj</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("solanaj");
        }

        /**
         * Dependency provider for <b>v100</b> with <b>org.p2p:solanaj</b> coordinates and
         * with version reference <b>p2pSolanaj</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getV100() {
            return create("solanaj.v100");
        }

        /**
         * Dependency provider for <b>v105</b> with <b>org.p2p:solanaj</b> coordinates and
         * with version reference <b>p2pSolanajVersion</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getV105() {
            return create("solanaj.v105");
        }

    }

    public static class VersionAccessors extends VersionFactory  {

        public VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>activity</b> with value <b>1.8.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getActivity() { return getVersion("activity"); }

        /**
         * Version alias <b>agp</b> with value <b>8.5.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAgp() { return getVersion("agp"); }

        /**
         * Version alias <b>appcompat</b> with value <b>1.6.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAppcompat() { return getVersion("appcompat"); }

        /**
         * Version alias <b>bitcoinjCore</b> with value <b>0.15.8</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getBitcoinjCore() { return getVersion("bitcoinjCore"); }

        /**
         * Version alias <b>constraintlayout</b> with value <b>2.1.4</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getConstraintlayout() { return getVersion("constraintlayout"); }

        /**
         * Version alias <b>converterGson</b> with value <b>2.9.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getConverterGson() { return getVersion("converterGson"); }

        /**
         * Version alias <b>coreKtx</b> with value <b>1.10.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getCoreKtx() { return getVersion("coreKtx"); }

        /**
         * Version alias <b>espressoCore</b> with value <b>3.5.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getEspressoCore() { return getVersion("espressoCore"); }

        /**
         * Version alias <b>javaIpfsHttpClient</b> with value <b>1.3.3</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getJavaIpfsHttpClient() { return getVersion("javaIpfsHttpClient"); }

        /**
         * Version alias <b>junit</b> with value <b>4.13.2</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getJunit() { return getVersion("junit"); }

        /**
         * Version alias <b>junitVersion</b> with value <b>1.1.5</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getJunitVersion() { return getVersion("junitVersion"); }

        /**
         * Version alias <b>kotlin</b> with value <b>1.9.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getKotlin() { return getVersion("kotlin"); }

        /**
         * Version alias <b>material</b> with value <b>1.10.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getMaterial() { return getVersion("material"); }

        /**
         * Version alias <b>mmorrellSolanaj</b> with value <b>1.17.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getMmorrellSolanaj() { return getVersion("mmorrellSolanaj"); }

        /**
         * Version alias <b>p2pSolanaj</b> with value <b>1.0.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getP2pSolanaj() { return getVersion("p2pSolanaj"); }

        /**
         * Version alias <b>p2pSolanajVersion</b> with value <b>1.0.5</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getP2pSolanajVersion() { return getVersion("p2pSolanajVersion"); }

        /**
         * Version alias <b>solana</b> with value <b>1.0.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getSolana() { return getVersion("solana"); }

        /**
         * Version alias <b>solanaj</b> with value <b>1.0.5</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getSolanaj() { return getVersion("solanaj"); }

        /**
         * Version alias <b>solanajVersion</b> with value <b>version</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getSolanajVersion() { return getVersion("solanajVersion"); }

    }

    public static class BundleAccessors extends BundleFactory {

        public BundleAccessors(ObjectFactory objects, ProviderFactory providers, DefaultVersionCatalog config, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) { super(objects, providers, config, attributesFactory, capabilityNotationParser); }

    }

    public static class PluginAccessors extends PluginFactory {
        private final AndroidPluginAccessors paccForAndroidPluginAccessors = new AndroidPluginAccessors(providers, config);
        private final JetbrainsPluginAccessors paccForJetbrainsPluginAccessors = new JetbrainsPluginAccessors(providers, config);

        public PluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of plugins at <b>plugins.android</b>
         */
        public AndroidPluginAccessors getAndroid() {
            return paccForAndroidPluginAccessors;
        }

        /**
         * Group of plugins at <b>plugins.jetbrains</b>
         */
        public JetbrainsPluginAccessors getJetbrains() {
            return paccForJetbrainsPluginAccessors;
        }

    }

    public static class AndroidPluginAccessors extends PluginFactory {

        public AndroidPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Plugin provider for <b>android.application</b> with plugin id <b>com.android.application</b> and
         * with version reference <b>agp</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getApplication() { return createPlugin("android.application"); }

    }

    public static class JetbrainsPluginAccessors extends PluginFactory {
        private final JetbrainsKotlinPluginAccessors paccForJetbrainsKotlinPluginAccessors = new JetbrainsKotlinPluginAccessors(providers, config);

        public JetbrainsPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of plugins at <b>plugins.jetbrains.kotlin</b>
         */
        public JetbrainsKotlinPluginAccessors getKotlin() {
            return paccForJetbrainsKotlinPluginAccessors;
        }

    }

    public static class JetbrainsKotlinPluginAccessors extends PluginFactory {

        public JetbrainsKotlinPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Plugin provider for <b>jetbrains.kotlin.android</b> with plugin id <b>org.jetbrains.kotlin.android</b> and
         * with version reference <b>kotlin</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getAndroid() { return createPlugin("jetbrains.kotlin.android"); }

    }

}
