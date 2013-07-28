api.metaClass.eventb_load { file ->
	def root = org.rodinp.core.RodinCore.valueOf(file).getRoot();
	def eventBTranslator = new de.prob.rodin.translate.EventBTranslator(root);
	def ebfactory = api.modelFactoryProvider.getEventBFactory();
	def model = ebfactory.load(eventBTranslator.getMainComponent(),
			eventBTranslator.getMachines(), eventBTranslator.getContexts(),
			eventBTranslator.getModelFile());
			
	def   writer = new java.io.StringWriter();
	def pto = new java.io.PrintWriter(writer);
	try {
		de.prob.ui.eventb.internal.TranslatorFactory.translate(root, pto);
	} catch (IllegalArgumentException e) {
		e.printStackTrace();
	}

	def s = model.getStatespace();

	def p2 = java.util.regex.Pattern.compile("^package\\((.*?)\\)\\.");
	def m2 = p2.matcher(writer.toString());
	m2.find();
	def cmd = m2.group(1);

	s.execute(new de.prob.animator.command.LoadEventBCommand(cmd));
	s.execute(new de.prob.animator.command.StartAnimationCommand());
			
	model
}