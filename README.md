# STILE test suite parallelizer replication package

## 1. Using Virtual Machine for replication

A virtual machine running Ubuntu Server 16.04 is available for download from Dropbox at https://www.dropbox.com/s/uqp4tojojmp4nmc/STILE%20GUI.ova?dl=0, and it contains this repository and all the dependencies needed to run STILE on the test suite subject.
It's strongly recommended to use this virtual machine to replicate the experiments, since STILE is still at a prototype level and has never been tested on different machines and configurations.
The virtual machine was created with Oracle VirtualBox 6.1 in the .ova format, a platform independent distribution format for virtual machine. It should be compatible with any virtualization software, although it has only been tested with VirtualBox. The recommended RAM requirement for the virtual machine is 8 GB.

Login credentials for the replication virtual machine:
	-username: `anonymous`
	-password: `fse19`

### 1.1 Troubleshooting

The virtual machine may start with a default resolution of 800x600. To enable full HD resolution (1920x1080), run the script /home/anonymous/setResolution.sh, then change resolution from Applications -> Settings -> Display

## 2. How to use STILE

### 2.1 Command Line

Start the virtual machine, login and give command `cd workspace/FSE19-submission-material/treeparallelizer`. The script run.sh starts STILE in command line mode. It takes two arguments: application under test and execution mode. To try the tree parallelization described in our paper, you should use `--extractAndRun` as second argument.
The available applications under test are:

	1. Addressbook
	2. Claroline
	3. Collabtive
	4. MantisBT
	5. MRBS
	6. PPMA

The results will be stored in the `treeparallelizer/results` directory, in detail:
	- `results/dot` will contain the prefix trees in DOT format
	- `results/json` will contain the prefix trees in JSON format
	- `results/png` will contain the images of prefix trees in PNG format
	
### 2.2 GUI
Start the virtual machine, login and give command `startx` to start the graphical environment. Then, move to the directory `/home/anonymous/workspace/FSE19-submission-material/treeparallelizer`. Run the script `runGUI.sh` with no arguments to start STILE with graphical user interface. The drop-down menu at the top of the window lets you choose the application under test, the buttons at the bottom of the window allow you respectively to:

	- 1. generate the warranted schedules for all tests with zero in-degree in the test dependency graph for the selected application
	- 2. generate the prefix tree for the selected application
	- 3. run the test suite in parallel
	- 4. display a previously computed prefix tree

For more details, watch the demo video at https://sepl.dibris.unige.it/STILE.php

### 2.3 Add a new test suite
To run a new test suite with STILE, you need to:

		- 1. Compute the test dependency graph of the test suite with TEDD. Please refer to TEDD's code, documentation and paper to know how to use TEDD to compute the graph ( https://github.com/matteobiagiola/FSE19-submission-material-TEDD )
		- 2. Create a Docker image of the system under test
		- 3. Convert the test suite from JUnit to TestNG
		- 4. Create a Docker configuration file in JSON format containing the information required to instantiate a container with the system under test (see the precise content that they must include from existing configuration files in `treeparallelizer/src/main/resources/app_config` )
		- 5. Copy the .properties files used by TEDD to configure test suites in the `config_files` directory

When you start STILE in GUI mode, the new application will appear in the drop down menu. In CLI mode, it now can be passed as first argument.
