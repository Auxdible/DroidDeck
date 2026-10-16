package com.droiddeck.launcher.gpu

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class LinuxVulkanDriverManagerTest {
    @Test fun turnipAndRadvLibrariesKeepTheirOwnStoredName() {
        assertEquals("libvulkan_freedreno.so", LinuxVulkanDriverManager.storedName("libvulkan_freedreno.so"))
        assertEquals("libvulkan_freedreno.so", LinuxVulkanDriverManager.storedName("libvulkan_freedreno-25.1.so"))
        assertEquals("libvulkan_radeon.so", LinuxVulkanDriverManager.storedName("libvulkan_radeon.so"))
        assertNull(LinuxVulkanDriverManager.storedName("vulkan.radeon.so"))
        assertNull(LinuxVulkanDriverManager.storedName("vulkan.ad07XX.so"))
        assertNull(LinuxVulkanDriverManager.storedName("libvulkan_radeon.so.1"))
    }
}
