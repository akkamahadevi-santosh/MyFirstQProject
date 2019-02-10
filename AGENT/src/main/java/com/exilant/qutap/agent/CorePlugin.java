package com.exilant.qutap.agent;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.java.plugin.PluginManager;
import org.java.plugin.PluginManager.PluginLocation;
import org.java.plugin.boot.Application;
import org.java.plugin.boot.ApplicationPlugin;
import org.java.plugin.registry.Extension;
import org.java.plugin.registry.ExtensionPoint;
import org.java.plugin.registry.PluginDescriptor;
import org.java.plugin.standard.StandardPluginLocation;
import org.java.plugin.util.ExtendedProperties;

import com.exilant.qutap.agent.server.AgentMain;
import com.exilant.qutap.agent.service.Installer;

/**
 * The Class CorePlugin.
 */
public class CorePlugin extends ApplicationPlugin implements Application {

	/** The log. */
	protected final Log LOG = LogFactory.getLog(getClass());

	/** The p manager. */
	public static PluginManager pManager;

	/** The pd. */
	public static PluginDescriptor pd;

	/** The cp. */
	public static CorePlugin cp;

	/** The runner. */
	private AgentMain runner;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.java.plugin.boot.ApplicationPlugin#initApplication(org.java.plugin.
	 * util.ExtendedProperties, java.lang.String[])
	 */
	@Override
	protected Application initApplication(ExtendedProperties config, String[] args) throws Exception {
		runner = new AgentMain();
		LOG.info("Agent initializing.....");
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.java.plugin.Plugin#doStart()
	 */
	@Override
	protected void doStart() throws Exception {
		LOG.info("Agent re-initializing.....");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.java.plugin.Plugin#doStop()
	 */
	@Override
	protected void doStop() throws Exception {
		LOG.info("Agent Stop");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.java.plugin.boot.Application#startApplication()
	 */
	@Override
	public void startApplication() throws Exception {
		LOG.info("initializing plugin system....");
		pManager = getManager();
		pd = getDescriptor();
		cp = this;
		LOG.info("plugin system initialized....");
		try {
			LOG.info("register agent to primary server");
			runner.registerAgent();
			LOG.info("Registration complete");
			runner.agentBootStrap();
			LOG.info("Bootstraping agent complete....");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void reInitiazePlugin(String plugin) throws Exception {

		pManager = getManager();
		PluginLocation pluginLocation = null;
		File dir = new File(Installer.PLUGINPATH + plugin);
		pluginLocation = StandardPluginLocation.create(dir);

		pManager.publishPlugins(new PluginLocation[] { pluginLocation });

		ExtensionPoint toolExtPoint = null;
		Object task = null;
		toolExtPoint = CorePlugin.pManager.getRegistry().getExtensionPoint(CorePlugin.cp.getDescriptor().getId(),
				"Task");

		Extension ext = toolExtPoint.getConnectedExtension(plugin);

		CorePlugin.pManager.activatePlugin(ext.getDeclaringPluginDescriptor().getId());

	}
	
	// old code 
	
//	public void reInitiazePlugin() throws Exception {
//		LOG.info("Re-initializing plugin system....");
//		pManager = getManager();
//		pd = getDescriptor();
//		cp = this;
//		LOG.info("plugin system Re-initialized....");
//	}


}
