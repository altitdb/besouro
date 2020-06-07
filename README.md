# Butterfly

This is a tool to measure TDD conformance. The classifications are:

#### Test Addition
#### Test-first
#### Test-last
#### Test Driven Development
#### Refactoring
#### Unknown

# How To Build Butterfly

First of all, you need to download the Eclipse SDK, containing the Eclipse Plug-in Development Environment (PDE). 
You can download it from [here](http://www.eclipse.org/pde/)

Download Butterfly's source files from [here](https://github.com/altitdb/butterfly).
You can simply download the files, you can clone it with git, if you want to modify it, 
or you can fork it in github if you intend to contribute your changes back to Butterfly.
	
These projects are not part of the plugin code itself. You can create them with eclipse wizards if you prefer. They are being provided just for simplicity.

Then open the site.xml file inside the butterfly-site project and press the 'build' button. 
Eclipse will compile everything and put the binaries inside the update site project.

This project is now a complete update site you can point your eclipse to. You can also make this directory 
available in a HTTP URL in order to make the plugin available for installing in other Eclipses instances.

It should be enough. If you have any problem, don't hesitate to send me an email at altitdb at gmail dot com.

# Installing Butterfly

Requirements: Java 11 and Eclipse 2020-03 (4.15.0)

Update Site: [https://butterfly-plugin.firebaseapp.com](https://butterfly-plugin.firebaseapp.com)

Launch Eclipse. Select Help -> Install New Software..., and add the update site to the list of available sites. Follow the wizard and you are done!

# Enabling Butterfly

Launch Eclipse. Select Window -> Show View -> Other..., and find for Butterfly view and click in Open. Select your project and click in Play Icon at Butterfly view.

# Giving feedback

Please let me know what do you think about Butterfly. Send me an email at altitdb at gmail dot com anytime!
