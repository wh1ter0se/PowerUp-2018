# 7593fc32521b145675cd06984830a1e89fe9bf16 encoding: utf-8
# @@ 1

########################### DO NOT MODIFY THIS FILE ###########################
#       This file was automatically generated by JRubyFX-fxmlloader on        #
# 2016-01-06 20:19:14 -0500 for ../sfx/plugins/built-in/subsystem/subsystem.fxml
########################### DO NOT MODIFY THIS FILE ###########################

module JRubyFX
  module GeneratedAssets
    class AOTc43f0db19d95c1f21829c7e961d536a3fbb293dd
      include JRubyFX
          def __build_via_jit(__local_fxml_controller, __local_namespace, __local_jruby_ext)
      __local_fx_id_setter = lambda do |name, __i|
        __local_namespace[name] = __i
        __local_fxml_controller.instance_variable_set(("@#{name}").to_sym, __i)
      end
(__local_sem_inst = Java.javax.script.ScriptEngineManager.new).setBindings(javax.script.SimpleBindings.new(__local_namespace))
(__local_sem_lang_inst_javascript = __local_sem_inst.getEngineByName("javascript")).setBindings(__local_sem_inst.getBindings(), javax.script.ScriptContext.ENGINE_SCOPE)
build(Java::DashfxControls::DataHBox) do
 setId("base")
 __local_fx_id_setter.call("base", self)
 __local_jruby_ext[:on_root_set].call(self) if __local_jruby_ext[:on_root_set]
 __local_sem_lang_inst_javascript.eval("\n\t\tvar nonelbll = null\n\t\tvar commandd = null;\n\t\tvar swapper = function(ov, old, running) {\n\t\t\tcommandd.setVisible(running);\n\t\t\tnonelbll.setVisible(!running);\n\t\t};\n\t\tvar runnerVp = null;\n\t\tfunction replaced()\n\t\t{\n\t\t\trunnerVp = base.getObservable(\"hasCommand\");\n\t\t\trunnerVp[\"addListener(javafx.beans.value.ChangeListener)\"](swapper);\n\t\t\tnonelbll = nonelbl;\n\t\t\tcommandd = command;\n\t\t\tif (typeof runnerVp == \"boolean\")\n\t\t\t{\n\t\t\t\tswapper(null, null, runnerVp.getValue());\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\tswapper(null, null, false);\n\t\t\t}\n\t\t}\n\t")
 getChildren.add(build(Java::JavafxSceneControl::Label) do
  textProperty.bind(RRExpressionValue.new(__local_namespace, Java::org.jruby.jfx8.Expression.valueOf("base.baseName"), Java::java::lang::String.java_class))
 end)
 getChildren.add(build(Java::JavafxSceneControl::Label) do
  setText(": ")
 end)
 getChildren.add(build(Java::DashfxLibControlsFxmlutils::CollapsableHBox) do
  getChildren.add(build(Java::JavafxSceneControl::Label) do
   setId("nonelbl")
   __local_fx_id_setter.call("nonelbl", self)
   setText("None")
  end)
  getChildren.add(build(Java::DashfxLibControls::Placeholder) do
   setId("command")
   __local_fx_id_setter.call("command", self)
   setControlPath("Label")
   setPropList("name: command")
   setMaxHeight(1.7976931348623157e+308)
   setMaxWidth(1.7976931348623157e+308)
  end)
 end)
 setStyle("/*Intentionally blank*/")
 setDataMode(Java::dashfx::lib::data::DataPaneMode::Nested)
 setAlignment(Java::javafx::geometry::Pos::CENTER_LEFT)
 setOnRegisterRequest(ScriptEventHandler.new("replaced()", __local_sem_lang_inst_javascript))
end
    end

      def hash
        "7593fc32521b145675cd06984830a1e89fe9bf16"
      end
      def compiled?
        true
      end
    end
  end
end
