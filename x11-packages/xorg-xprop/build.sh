TERMUX_PKG_HOMEPAGE=https://xorg.freedesktop.org/
TERMUX_PKG_DESCRIPTION="Utility to print properties of X11 windows"
TERMUX_PKG_LICENSE="MIT, HPND"
TERMUX_PKG_MAINTAINER="@termux"
TERMUX_PKG_VERSION="1.2.8"
TERMUX_PKG_SRCURL=https://xorg.freedesktop.org/releases/individual/app/xprop-${TERMUX_PKG_VERSION}.tar.xz
TERMUX_PKG_SHA256=d689e2adb7ef7b439f6469b51cda8a7daefc83243854c2a3b8f84d0f029d67ee
TERMUX_PKG_AUTO_UPDATE=true
TERMUX_PKG_DEPENDS="libx11"
TERMUX_PKG_BUILD_DEPENDS="xorg-util-macros"