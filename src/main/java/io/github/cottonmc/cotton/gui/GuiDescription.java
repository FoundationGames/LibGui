package io.github.cottonmc.cotton.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.screen.PropertyDelegate;

import io.github.cottonmc.cotton.gui.impl.FocusHandler;
import io.github.cottonmc.cotton.gui.widget.WPanel;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import org.jetbrains.annotations.Nullable;

/**
 * A GUI description represents a GUI without depending on screens.
 *
 * <p>GUI descriptions contain the root panel and the property delegate of the GUI.
 * They also manage the focused widget.
 *
 * @see io.github.cottonmc.cotton.gui.client.LightweightGuiDescription
 * @see SyncedGuiDescription
 */
public interface GuiDescription {
	public WPanel getRootPanel();
	public int getTitleColor();
	
	public GuiDescription setRootPanel(WPanel panel);

	/**
	 * Sets the title color of this GUI.
	 *
	 * <p>The dark-mode title color will also be set by this method.
	 * If the specified color is {@link io.github.cottonmc.cotton.gui.widget.WLabel#DEFAULT_TEXT_COLOR},
	 * the dark-mode color will be {@link io.github.cottonmc.cotton.gui.widget.WLabel#DEFAULT_DARKMODE_TEXT_COLOR};
	 * otherwise it will be the specified color.
	 *
	 * @param color the new title color
	 * @return this GUI
	 */
	public GuiDescription setTitleColor(int color);

	/**
	 * Sets the light and dark title colors of this GUI.
	 *
	 * @param lightColor the light-mode color
	 * @param darkColor  the dark-mode color
	 * @return this GUI
	 * @since 2.1.0
	 */
	GuiDescription setTitleColor(int lightColor, int darkColor);

	/** Sets the object which manages the integer properties used by WBars */
	public GuiDescription setPropertyDelegate(PropertyDelegate delegate);
	
	/** Typical users won't call this. This adds a Slot to Container/Controller-based guis, and does nothing on lightweight guis. */
	public void addSlotPeer(ValidatedSlot slot);
	
	/** Guis should use this method to add clientside styles and BackgroundPainters to their controls */
	@Environment(EnvType.CLIENT)
	public void addPainters();
	
	/** Gets the object which manages the integer properties used by WBars and such. */
	@Nullable
	public PropertyDelegate getPropertyDelegate();
	
	/** Tests whether the widget is the currently-focused one. */
	public boolean isFocused(WWidget widget);
	
	/** Gets the currently-focused WWidget. May be null. */
	@Nullable
	public WWidget getFocus();
	
	/** Notifies this gui that the widget wants to acquire focus. */
	public void requestFocus(WWidget widget);
	
	/** Notifies this gui that the widget wants to give up its hold over focus. */
	public void releaseFocus(WWidget widget);

	/**
	 * Cycles the focused widget in the GUI.
	 *
	 * @param lookForwards whether this should cycle forwards (true) or backwards (false)
	 * @since 2.0.0
	 */
	default void cycleFocus(boolean lookForwards) {
		FocusHandler.cycleFocus(this, lookForwards);
	}

	/**
	 * Gets whether this GUI is fullscreen.
	 *
	 * <p>Fullscreen GUIs have no default background painter and
	 * have the root panel stretched to fit the entire screen on the client.
	 *
	 * @return true if this GUI is fullscreen, false otherwise
	 * @since 2.0.0
	 */
	boolean isFullscreen();

	/**
	 * Sets whether this GUI is fullscreen.
	 *
	 * @param fullscreen true if this GUI is fullscreen, false otherwise
	 * @since 2.0.0
	 */
	void setFullscreen(boolean fullscreen);

	/**
	 * Gets whether the title of this GUI should be rendered by the screen.
	 *
	 * <p>Modders can disable this to render the title themselves with a widget.
	 *
	 * @return true if the title is visible, false otherwise
	 * @since 2.0.0
	 */
	boolean isTitleVisible();

	/**
	 * Sets whether the title of this GUI should be rendered by the screen.
	 *
	 * @param titleVisible true if the title is visible, false otherwise
	 * @since 2.0.0
	 */
	void setTitleVisible(boolean titleVisible);

	/**
	 * Gets the horizontal alignment of the GUI title.
	 *
	 * @return the alignment
	 * @since 2.1.0
	 */
	HorizontalAlignment getTitleAlignment();

	/**
	 * Sets the horizontal alignment of the GUI title.
	 *
	 * @param alignment the new alignment
	 * @since 2.1.0
	 */
	void setTitleAlignment(HorizontalAlignment alignment);
}
