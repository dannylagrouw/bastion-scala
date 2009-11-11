require 'buildr/scala'

options.test = false

VERSION_NUMBER = "0.1"
GROUP = "bastion-scala"
COPYRIGHT = "Copyright 2009 Danny Lagrouw"

repositories.remote << "http://www.ibiblio.org/maven2/"

desc "Bastion is a Scala framework for implementing Domain-Driven Designed (DDD) applications."
define "bastion-scala" do

  project.version = VERSION_NUMBER
  project.group = GROUP
  manifest["Implementation-Vendor"] = COPYRIGHT

  define "bastion-main" do
    test.exclude '*'
  end

  define "bastion-mem" do
    compile.with(project('bastion-main'))
  end

end
