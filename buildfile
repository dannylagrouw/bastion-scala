require 'buildr/scala'

Buildr.settings.build['scala.test'] = "1.0" 

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
	package :jar
  end

  define "bastion-mem" do
    package :jar
    compile.with(project('bastion-main'))
  end

end

# Temp hack to get ScalaTest 1.0 / Scala 2.8 running.
module Buildr::Scala
  class ScalaTest
    class << self
      def dependencies
        ["org.scalatest:scalatest:jar:1.0"] + Check.dependencies +
          JMock.dependencies + JUnit.dependencies
      end
    end
  end
end 

