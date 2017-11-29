# Copyright (C) 2013 patrick
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.

module SD::CoreExt
  class TabSwitcher < Java::JavafxSceneLayout::HBox
    include JRubyFX
    def initialize
      super()
      self.spacing = 6
      self.padding = [6,6,6,6]
      self.style_class << "tab-switcher"
      @designer = SD::Designer.instance
      rebuild_menu
      @designer.view_controllers.add_change_listener do
        rebuild_menu
      end
      @designer.vc_index_property.add_change_listener do
        refocus_menu
      end
    end

    def rebuild_menu
      @bt_map = {}
      children.clear
      children.add_all *@designer.view_controllers.map { |vc|
        Button.new(vc.name).tap do |btn|
          btn.set_on_action {@designer.tab_select vc.tab}
          @bt_map[btn] = vc
        end
      }.tap {|btns| @btns = btns; btns[@designer.vc_index].style_class << "active" }
    end

    def refocus_menu
      @btns.each {|btn| btn.style_class.remove "active"}
      @btns[@designer.vc_index].style_class << "active"
    end

    def getUi
      return self
    end

    def ui
      return self
    end

    def registered(stuff)
      # do nut'in
    end
  end
end
